package com.example.springkafkagracefulshutdown;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class SpringKafkaGracefulShutdownApplication implements CommandLineRunner {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaGracefulShutdownApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        for (int i = 0; i < 9; i++) {
//            this.kafkaTemplate.send("test", 0, null, "foo" + i);
//        }
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }


    @Bean
    public KafkaAdmin.NewTopics topic() {
        return new KafkaAdmin.NewTopics(TopicBuilder.name("test")
            .partitions(1)
            .replicas(1)
            .build());
    }

    @KafkaListener(topics = "test", id = "test.offset.1", concurrency = "1")
    void listenTopic(@Header(KafkaHeaders.OFFSET) long offset) throws InterruptedException {
        log.info("Topic Consuming: " + offset);
        //Thread.sleep(10000L);
        Thread.sleep(1000L);
    }
}
