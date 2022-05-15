package com.example.kafkarepartitioning.repartition;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {

    @KafkaListener(id = "seekExample", topics = "seekExample", groupId = "seek")
    void consumer(ConsumerRecord<String, String> in) {
        System.out.println(in.key() + " : " + in.value());
    }
}
