package com.example.kafkarebalancing;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@Slf4j
public class KafkaRepartitioningApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaRepartitioningApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<String, String> template;

    @Override
    public void run(String... args) throws Exception {
        this.template.send("myTopic", "foo1");
        this.template.send("myTopic", "foo2");
        this.template.send("myTopic", "foo3");
        log.info("All received");
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("myTopic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @KafkaListener(topics = "myTopic", id = "my.group.id")
    void consumer(ConsumerRecord<String, String> in) {
        System.out.println("Topic");
        System.out.println(in.key() + " : " + in.value());
    }

}
