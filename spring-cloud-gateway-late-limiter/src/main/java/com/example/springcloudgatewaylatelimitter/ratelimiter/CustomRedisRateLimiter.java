package com.example.springcloudgatewaylatelimitter.ratelimiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@Slf4j
@Primary
public class CustomRedisRateLimiter extends RedisRateLimiter {

    private final Config userAConfig = new Config().setBurstCapacity(10).setReplenishRate(1).setRequestedTokens(1);
    private final Config commonUserConfig = new Config().setBurstCapacity(1).setReplenishRate(1).setRequestedTokens(1);
    private ReactiveStringRedisTemplate redisTemplate;

    private RedisScript<List<Long>> script;

    @Autowired
    public CustomRedisRateLimiter(ReactiveStringRedisTemplate redisTemplate, RedisScript<List<Long>> script,
        ConfigurationService configurationService) {
        super(redisTemplate, script, configurationService);
        this.redisTemplate = redisTemplate;
        this.script = script;
    }

    public CustomRedisRateLimiter(int defaultReplenishRate, int defaultBurstCapacity) {
        super(defaultReplenishRate, defaultBurstCapacity);
    }

    public CustomRedisRateLimiter(int defaultReplenishRate, int defaultBurstCapacity, int defaultRequestedTokens) {
        super(defaultReplenishRate, defaultBurstCapacity, defaultRequestedTokens);
    }

    static List<String> getKeys(String id) {
        String prefix = "request_rate_limiter.{" + id;
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        Config routeConfig = loadConfiguration(routeId, id);

        int replenishRate = routeConfig.getReplenishRate();
        int burstCapacity = routeConfig.getBurstCapacity();
        int requestedTokens = routeConfig.getRequestedTokens();

        try {
            List<String> keys = getKeys(id);

            List<String> scriptArgs = Arrays.asList(replenishRate + "", burstCapacity + "", "", requestedTokens + "");
            Flux<List<Long>> flux = redisTemplate.execute(this.script, keys, scriptArgs);
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
        if (key.equals("demo1234")) {
            return getConfig().getOrDefault(routeId + key, userAConfig);
        }
        return getConfig().getOrDefault(routeId, commonUserConfig);

    }

}