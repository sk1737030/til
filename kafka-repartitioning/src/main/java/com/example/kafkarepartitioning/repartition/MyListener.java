package com.example.kafkarepartitioning.repartition;

import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class MyListener extends AbstractConsumerSeekAware {

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().forEach(tp -> System.out.println(tp));

    }
}
