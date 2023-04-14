package dgle.restassured.other.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "orderV2",
    url = "http://localhost:9090/v2/other-client"
)
public interface OtherOrderClientV2 {

  @GetMapping("/{id]}")
  Long orderAmount(@PathVariable Long id);

}
