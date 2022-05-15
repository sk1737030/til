package com.example.simplekafka;

import com.example.simplekafka.producer.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class SimpleKafkaApplication implements CommandLineRunner {

    public static final String MATCHING_DATA = "mcs.local.matching.data";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SimpleKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        kafkaTemplate.send(MATCHING_DATA, fixture());
    }

    private Order fixture() {
        return Order.builder()
            .orderId(1)
            .price("2000")
            .orderType("Wait")
            .build();
    }

}
