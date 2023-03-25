package dgle.restassured;


import dgle.restassured.other.client.OtherOrderClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FakeOtherOrderClient implements OtherOrderClient {

  @Override
  public Long orderAmount(Long id) {
    return 10000L;
  }

}
