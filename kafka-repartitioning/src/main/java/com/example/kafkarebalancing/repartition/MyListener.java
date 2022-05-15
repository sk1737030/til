package com.example.kafkarebalancing.repartition;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
public class MyListener implements ConsumerAwareRebalanceListener {

    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        ConsumerAwareRebalanceListener.super.onPartitionsAssigned(consumer, partitions);
        partitions.forEach(topicPartition -> System.out.println(topicPartition.partition()));
    }
}
