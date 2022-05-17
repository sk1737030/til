package com.example.kafkarebalancing.repartition;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Slf4j
@Component
public class MyListener implements ConsumerAwareRebalanceListener {

    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        ConsumerAwareRebalanceListener.super.onPartitionsAssigned(consumer, partitions);
        log.info("onPartitionsAssigned");
        partitions.forEach(topicPartition -> System.out.println(topicPartition.partition()));
    }

    @Override
    public void onPartitionsLost(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        ConsumerAwareRebalanceListener.super.onPartitionsLost(consumer, partitions);
        log.info("onPartitionsLost");
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        ConsumerAwareRebalanceListener.super.onPartitionsRevoked(partitions);
        log.info("onPartitionsRevoked");
    }
}
