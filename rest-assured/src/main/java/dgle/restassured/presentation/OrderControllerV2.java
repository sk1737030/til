package dgle.restassured.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/v2/order")
public class OrderControllerV2 {

  private final WrapperOrderClient wrapperOrderClient;

  public OrderControllerV2(WrapperOrderClient wrapperOrderClient) {
    this.wrapperOrderClient = wrapperOrderClient;
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
    Long amount = wrapperOrderClient.orderAmount(orderId);
    return ResponseEntity.ok(new OrderResponse(orderId, amount, 1000L));
  }

  public static class OrderResponse {

    private Long orderId;
    private Long amount;
    private Long userId;

    public OrderResponse(Long orderId, Long amount, Long userId) {
      this.orderId = orderId;
      this.amount = amount;
      this.userId = userId;
    }

    public Long getOrderId() {
      return orderId;
    }

    public Long getAmount() {
      return amount;
    }

    public Long getUserId() {
      return userId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      OrderResponse that = (OrderResponse) o;
      return Objects.equals(orderId, that.orderId) && Objects.equals(amount, that.amount) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(orderId, amount, userId);
    }

  }

}
