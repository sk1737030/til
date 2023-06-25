package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

  private int connectMax = 5;
  private int acquireTimeout = 5000;
  private int connectTimeout = 5000;
  private int responseTimeout = 5000;
  private int readTimeout = 5000;
  private int writeTimeout = 5000;

  private final ObjectMapper objectMapper;

  public PlatformWebClientFactory webClientFactory() {
    ConnectionProvider provider = ConnectionProvider.builder("provider")
      .maxConnections(connectMax)
      .pendingAcquireTimeout(Duration.ofMillis(acquireTimeout))
      .build();

    /**
     * HttpClient timeout 지정
     * @return
     */
    HttpClient httpClient = HttpClient.create(provider)
      .compress(true)
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
      .responseTimeout(Duration.ofMillis(responseTimeout))
      .keepAlive(true)
      .doOnConnected(conn ->
        conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
          .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS))
      );

    ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

    return new PlatformWebClientFactory(connector);
  }

  public class PlatformWebClientFactory {
    private ReactorClientHttpConnector connector;

    public PlatformWebClientFactory(ReactorClientHttpConnector connector) {
      this.connector = connector;
    }

    public WebClient getClient(String baseUrl) {
      Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(objectMapper);
      Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(objectMapper);

      // HTTP 코덱 설정
      ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
        .codecs(codecConfigurer -> {
          codecConfigurer.defaultCodecs().jackson2JsonEncoder(encoder);
          codecConfigurer.defaultCodecs().jackson2JsonDecoder(decoder);
          codecConfigurer.defaultCodecs().maxInMemorySize(1024 * 1024); // 1MB
        }).build();

      return WebClient.builder()
        .baseUrl(baseUrl)
        .clientConnector(connector) // HTTP 클라이언트 라이브러리 셋팅
        .exchangeStrategies(exchangeStrategies) // HTTP 메세지 reader/writer 커스텀
        .build();
    }
  }

  @Bean
  public WebClient accommodationWarClient() {
    String baseUrl = "http://localhost:19000";
    return this.webClientFactory().getClient(baseUrl);
  }

}
