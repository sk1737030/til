package com.example.hwan1.hello.core.web;

import com.example.hwan1.hello.core.common.MyLogger;
import com.example.hwan1.hello.core.service.LogDemoService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestUrl = request.getRequestURI().toString();
        myLogger.setRequestURL(requestUrl);

        myLogger.log("controller test");
        logDemoService.log("testId");
        return "OK";
    }
}
