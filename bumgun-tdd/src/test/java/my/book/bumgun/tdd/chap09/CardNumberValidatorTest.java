package my.book.bumgun.tdd.chap09;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import my.book.bumgun.tdd.chap07.CardValidity;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class CardNumberValidatorTest {


    @Test
    void test() {
        stubFor(post("/card")
            .withRequestBody(equalTo("1234567890"))
            .willReturn(aResponse()
                .withBody("ok")
                .withHeader("Content-Type", "text/plain")));

        CardNumberValidator validator = new CardNumberValidator("http://localhost:8080");
        CardValidity val = validator.validate("1234567890");
        assertEquals(CardValidity.VALID, val);
    }

    @Test
    void timeout() {
        stubFor(post("/card")
            .withRequestBody(equalTo("1234567890"))
            .willReturn(aResponse()
                .withFixedDelay(10000)));

        CardNumberValidator validator = new CardNumberValidator("http://localhost:8080");
        CardValidity val = validator.validate("1234567890");
        assertEquals(CardValidity.TIMEOUT, val);
    }

}