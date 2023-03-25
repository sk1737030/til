package dgle.restassured;


import dgle.restassured.other.client.OtherOrderClientV2;
import dgle.restassured.presentation.WrapperOrderClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FakeOtherOrderClientV2 extends WrapperOrderClient {

  public FakeOtherOrderClientV2(OtherOrderClientV2 otherOrderClientV2) {
    super(null);
  }

  @Override
  public Long orderAmount(Long id) {
    return 10000L;
  }

}
