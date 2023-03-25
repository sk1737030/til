package dgle.restassured.presentation;

import dgle.restassured.other.client.OtherOrderClientV2;
import org.springframework.stereotype.Service;

@Service
public class WrapperOrderClient {

  private final OtherOrderClientV2 otherOrderClientV2;

  public WrapperOrderClient(OtherOrderClientV2 otherOrderClientV2) {
    this.otherOrderClientV2 = otherOrderClientV2;
  }

  public Long orderAmount(Long id) {
    return otherOrderClientV2.orderAmount(id);
  }

}
