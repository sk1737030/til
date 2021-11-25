package com.example.hwan1.hello.core.service;

import com.example.hwan1.hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final ObjectProvider<MyLogger> myLoggerProvider;

    public void log(String testId) {
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id = " + testId);
    }
}
