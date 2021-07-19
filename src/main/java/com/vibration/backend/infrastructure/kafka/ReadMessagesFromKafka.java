package com.vibration.backend.infrastructure.kafka;

import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class ReadMessagesFromKafka {

    private final KafkaStreams kafkaStreams;

    public ReadMessagesFromKafka(KafkaStreams kafkaStreams) {
        this.kafkaStreams = kafkaStreams;
    }

    public List<CoordinatesData> execute(User user) {
        final ReadOnlyKeyValueStore<String, CoordinatesAggregate> coordinatesAggregateKeyStore =
                kafkaStreams.store(StoreQueryParameters.fromNameAndType("CoordinatesStore", QueryableStoreTypes.keyValueStore()));
        var coordinatesAggregate = coordinatesAggregateKeyStore.get(user.getUserName());
        return Objects.isNull(coordinatesAggregate) ? new LinkedList<>() : coordinatesAggregate.getCoordinatesList();
    }
}
