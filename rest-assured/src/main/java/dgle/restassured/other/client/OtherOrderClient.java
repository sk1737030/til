package dgle.restassured.other.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "order",
    url = "http://localhost:8080/other-client",
    primary = false
)
public interface OtherOrderClient {

  @GetMapping("/{id]}")
  Long orderAmount(@PathVariable Long id);

}
