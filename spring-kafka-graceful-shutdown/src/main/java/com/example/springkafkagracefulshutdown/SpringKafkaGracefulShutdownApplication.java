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
        for (int i = 0; i < 9; i++) {
            this.kafkaTemplate.send("myTopic", "foo" + i);
        }
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }


    @Bean
    public KafkaAdmin.NewTopics topic() {
        return new KafkaAdmin.NewTopics(TopicBuilder.name("myTopic")
            .partitions(3)
            .replicas(1)
            .build());
    }

    @KafkaListener(topics = "myTopic", id = "my.group.id", concurrency = "1")
    void listenTopic() throws InterruptedException {
        log.info("Topic Consuming");
        Thread.sleep(10000L);
    }


}
