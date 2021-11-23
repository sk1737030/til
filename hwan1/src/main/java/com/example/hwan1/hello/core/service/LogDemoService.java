package com.example.hwan1.hello.core.service;

import com.example.hwan1.hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger;

    public void log(String testId) {
        myLogger.log("service id = " + testId);
    }
}
