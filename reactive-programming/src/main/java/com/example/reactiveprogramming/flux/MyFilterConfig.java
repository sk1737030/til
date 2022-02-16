package com.example.reactiveprogramming.flux;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterConfig {

    @Autowired
    private EventNotify eventNotify;

    @Bean
    public FilterRegistrationBean<Filter> addFilter1() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter(eventNotify));
        bean.addUrlPatterns("/sse");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<Filter> addFilter2() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter2(eventNotify));
        bean.addUrlPatterns("/add");
        return bean;
    }
}
