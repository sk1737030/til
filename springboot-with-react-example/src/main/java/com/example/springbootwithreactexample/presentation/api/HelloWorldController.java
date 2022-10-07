package com.example.springbootwithreactexample.presentation.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public List<String> test() {
        return Arrays.asList("안녕하세요", "Hello");
    }

}
