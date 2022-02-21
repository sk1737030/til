package com.example.springcloudgateway.javaconfig;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator bRoue(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("q-service", r -> r.path("/q-service/**")
                .uri("http://localhost:18081"))
            .route("q-service", r -> r.path("/w-service/**")
                .uri("http://localhost:18080"))
            .build();
    }

    @Bean
    public RouteLocator cRoute(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("c-service", r -> r.path("/c-service/**")
                .uri("http://localhost:18081"))
            .route("d-service", r -> r.path("/d-service/**")
                .uri("http://localhost:18080"))
            .build();
    }

}
