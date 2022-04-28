package com.example.springcloudgatewaylatelimitter.ratelimiter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class UserKeyResolver {

    @Bean
    public KeyResolver apiKeyResolve() {
        return exchange -> {
            if (!exchange.getRequest().getQueryParams().containsKey("userId")) {
                return Mono.just("____EMPTY_KEY__");
            }

            return Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
        };
    }
}
