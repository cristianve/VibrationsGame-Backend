package com.vibration.backend.infrastructure.configuration;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class StartStopConfiguration {

    private final KafkaStreams kafkaStreams;

    public StartStopConfiguration(KafkaStreams kafkaStreams) {
        this.kafkaStreams = kafkaStreams;
    }

    @PostConstruct
    public void postConstruct() {
        this.kafkaStreams.start();
    }

    @PreDestroy
    public void preDestroy() {
        this.kafkaStreams.close();
    }
}
