package com.vibration.backend.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic coordinatesTopic() {
        return TopicBuilder.name("coordinates")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
