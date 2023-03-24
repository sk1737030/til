package dgle.restassured;

import dgle.restassured.presentation.OrderController;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestAssuredApplicationTests {

  protected static RequestSpecification REQUEST_SPEC;

  @LocalServerPort
  private Integer port;

  @BeforeEach
  public void setRequestSpec() {
    RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
    reqBuilder.setContentType(ContentType.JSON);
    reqBuilder.setPort(port);
    REQUEST_SPEC = reqBuilder.build();
  }

  @Test
  void getOrder() {
    ExtractableResponse<Response> actualResponse =
        RestAssured.
            given().
              spec(REQUEST_SPEC).log().all().
            when().
              get("/order/{orderId}", 1L).
            then().
              log().all().extract();

    assertThat(actualResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(actualResponse.jsonPath().getObject(".", OrderController.OrderResponse.class)).isEqualTo(new OrderController.OrderResponse(1L, 10000L, 1000L));
  }

}
