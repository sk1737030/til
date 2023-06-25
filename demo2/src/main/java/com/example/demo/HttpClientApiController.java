package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/skmn/client")
public class HttpClientApiController {

  @PostMapping
  public ResponseEntity<String> restMapping() throws InterruptedException {
    log.info("restMapping2");

    Thread.sleep(4500);

    if ((int) (Math.random() * 100) % 2 == 0) {
      if ((int) (Math.random() * 100) % 2 == 0) {
        if ((int) (Math.random() * 100) % 2 == 0) {
          return ResponseEntity.status(500).body("fail");
        } else
          throw new RuntimeException();
      } else {
        return ResponseEntity.badRequest().body("fail");
      }
    }


    return ResponseEntity.ok("ok");

  }

}
