package com.example.springcloudgatewaylatelimitter.ratelimiter;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.route.RouteDefinitionRouteLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@Slf4j
@Primary
public class CustomRedisRateLimiter extends AbstractRateLimiter<CustomRedisRateLimiter.Config> implements ApplicationContextAware {

    public static final String CONFIGURATION_PROPERTY_NAME = "redis-rate-limiter";
    public static final String REDIS_SCRIPT_NAME = "redisRequestRateLimiterScript";
    public static final String REMAINING_HEADER = "X-RateLimit-Remaining";
    public static final String REPLENISH_RATE_HEADER = "X-RateLimit-Replenish-Rate";
    public static final String BURST_CAPACITY_HEADER = "X-RateLimit-Burst-Capacity";
    public static final String REQUESTED_TOKENS_HEADER = "X-RateLimit-Requested-Tokens";

    private final Config getConfig = new Config(1, 10, 1);
    private final Config postConfig = new Config(1, 1, 1);

    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private ReactiveStringRedisTemplate redisTemplate;
    private RedisScript<List<Long>> script;

    protected CustomRedisRateLimiter(ReactiveStringRedisTemplate redisTemplate, RedisScript<List<Long>> script) {
        super(Config.class, CONFIGURATION_PROPERTY_NAME, null);
        this.redisTemplate = redisTemplate;
        this.script = script;
        this.initialized.compareAndSet(false, true);
    }

    static List<String> getKeys(String id) {
        String prefix = "request_rate_limiter.{" + id;
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (initialized.compareAndSet(false, true)) {
            if (this.redisTemplate == null) {
                this.redisTemplate = context.getBean(ReactiveStringRedisTemplate.class);
            }
            this.script = context.getBean(REDIS_SCRIPT_NAME, RedisScript.class);
        }
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        if (!this.initialized.get()) {
            throw new IllegalStateException("CustomRedisRateLimiter is not initialized");
        }

        Config routeConfig = loadConfiguration(routeId, id);

        // How many requests per second do you want a user to be allowed to do?
        int replenishRate = routeConfig.getReplenishRate();

        // How much bursting do you want to allow?
        int burstCapacity = routeConfig.getBurstCapacity();

        // How many tokens are requested per request?
        int requestedTokens = routeConfig.getRequestedTokens();

        try {
            List<String> keys = getKeys(id);

            List<String> scriptArgs = Arrays.asList(replenishRate + "", burstCapacity + "", "", requestedTokens + "");
            Flux<List<Long>> flux = this.redisTemplate.execute(this.script, keys, scriptArgs);
            return flux.onErrorResume(throwable -> {
                if (log.isDebugEnabled()) {
                    log.debug("Error calling rate limiter lua", throwable);
                }
                return Flux.just(Arrays.asList(1L, -1L));
            }).reduce(new ArrayList<Long>(), (longs, l) -> {
                longs.addAll(l);
                return longs;
            }).map(results -> {
                boolean allowed = results.get(0) == 1L;
                Long tokensLeft = results.get(1);

                Response response = new Response(allowed, getHeaders(routeConfig, tokensLeft));

                if (log.isDebugEnabled()) {
                    log.debug("response: " + response);
                }
                return response;
            });
        } catch (Exception e) {
            log.error("Error determining if user allowed from redis", e);
        }

        return Mono.just(new Response(true, getHeaders(routeConfig, -1L)));
    }

    Config loadConfiguration(String routeId, String key) {
        Config routeConfig;

        if (key.equals("demo1234")) {
            routeConfig = getConfig().getOrDefault(routeId + key, postConfig);
        } else {
            routeConfig = getConfig().getOrDefault(routeId, getConfig);
        }

        if (routeConfig == null) {
            routeConfig = getConfig().get(RouteDefinitionRouteLocator.DEFAULT_FILTERS);
        }

        if (routeConfig == null) {
            throw new IllegalArgumentException("No Configuration found for route " + routeId + " or defaultFilters");
        }

        return routeConfig;
    }

    @NotNull
    public Map<String, String> getHeaders(Config config, Long tokensLeft) {
        Map<String, String> headers = new HashMap<>();
        // configuration properties
        headers.put(REMAINING_HEADER, tokensLeft.toString());
        headers.put(REPLENISH_RATE_HEADER, String.valueOf(config.getReplenishRate()));
        headers.put(BURST_CAPACITY_HEADER, String.valueOf(config.getBurstCapacity()));
        headers.put(REQUESTED_TOKENS_HEADER, String.valueOf(config.getRequestedTokens()));
        return headers;
    }

    @Override
    public Map<String, Config> getConfig() {
        return new HashMap<>();
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public Config newConfig() {
        return new Config(1, 10, 1);
    }

    @ToString
    @Getter
    public static class Config {

        private final int replenishRate;
        private final int burstCapacity;
        private final int requestedTokens;

        public Config(int replenishRate, int burstCapacity, int requestedTokens) {
            this.replenishRate = replenishRate;
            this.burstCapacity = burstCapacity;
            this.requestedTokens = requestedTokens;
        }
    }
}