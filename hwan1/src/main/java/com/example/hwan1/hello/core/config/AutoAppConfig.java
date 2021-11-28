package com.example.hwan1.hello.core.config;

import com.example.hwan1.hello.core.discount.DisCountPolicy;
import com.example.hwan1.hello.core.discount.FixDiscountPolicy;
import com.example.hwan1.hello.core.discount.RateDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoAppConfig {

    @Bean
    DisCountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }

    @Bean
    DisCountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }
}
