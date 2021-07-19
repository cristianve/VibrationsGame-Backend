package com.vibration.backend.infrastructure.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
public class KafkaConsumerConfiguration {

    private static final Logger log = LogManager.getLogger(KafkaConsumerConfiguration.class);

    private static final String TOPIC = "coordinates";

    @Value("${spring.kafka.streams.application-id}")
    private String applicationId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.streams.state-dir}")
    private String stateDir;

    @Value("${spring.kafka.streams.properties.commit.interval.ms:0}")
    private Integer commitInterval;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, commitInterval);
        props.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, commitInterval);

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KafkaStreams coordinatesStream() {
        var streamsBuilder = new StreamsBuilder();
        streamsBuilder.stream(TOPIC,
                Consumed.with(Serdes.String(), new CoordinatesDataSerde()))
                .groupByKey()
                .aggregate(
                        CoordinatesAggregate::new,
                        (key, value, list) -> {
                            if (Objects.isNull(value.getTime())) {
                                log.debug("Deleting coordinates list for user {}", key);
                                return list.withCoordinatesList(null);
                            }
                            else {
                                log.debug("Added coordinates to list for user {}", key);
                                return list.withCoordinates(value);
                            }
                        },
                        Materialized.<String, CoordinatesAggregate, KeyValueStore<Bytes, byte[]>>as("CoordinatesStore")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new CoordinatesAggregateSerde())
                );
        return new KafkaStreams(streamsBuilder.build(), kStreamsConfigs().asProperties());
    }

}
