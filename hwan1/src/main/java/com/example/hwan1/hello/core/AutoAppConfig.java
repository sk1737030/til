package com.example.hwan1.hello.core;

import com.example.hwan1.hello.core.discount.DisCountPolicy;
import com.example.hwan1.hello.core.discount.FixDiscountPolicy;
import com.example.hwan1.hello.core.discount.RateDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {

    @Bean
    DisCountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }

    @Bean
    @Primary
    DisCountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }
}
