package com.example.hwan2.config;

import com.example.hwan2.log.logtrace.FieldLogTrace;
import com.example.hwan2.log.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }

}
