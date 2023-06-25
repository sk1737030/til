package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
public class HttpClientApiController {

  private static final String AUTHENTICATION_NAME = "Authentication";

  private final WebClient client;

  public HttpClientApiController(@Qualifier("accommodationWarClient") WebClient client) {
    this.client = client;
  }


  @GetMapping("/hello")
  public void httpClientTest() {
    log.info("hello 8080");
    try {
      client.post()
        .uri("/skmn/client")
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHENTICATION_NAME, "asdposajdpsaoj")
        .bodyValue("Test")
        .retrieve()
        .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(), response -> {
          throw new IllegalArgumentException();
        })
        .bodyToMono(String.class)
        .block();
    } catch (IllegalArgumentException e) {
      log.info("Exception");
    }
  }

}
