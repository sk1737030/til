package com.example.restdocsexample.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

  @GetMapping("/check")
  public ResponseEntity<Map<String, String>> check() {
    return ResponseEntity.ok(Map.of("ok", "ok"));
  }

}
