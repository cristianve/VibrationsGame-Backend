package com.vibration.backend.infrastructure.kafka;

import com.google.gson.Gson;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class CoordinatesAggregateSerde extends Serdes.WrapperSerde<CoordinatesAggregate> {

    public CoordinatesAggregateSerde() {
        super(new CoordinatesAggregateSerializer(), new CoordinatesAggregateDeserializer());
    }

    static class CoordinatesAggregateSerializer implements Serializer<CoordinatesAggregate> {

        @Override
        public byte[] serialize(String topic, CoordinatesAggregate data) {
            return data.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    static class CoordinatesAggregateDeserializer implements Deserializer<CoordinatesAggregate> {

        @Override
        public CoordinatesAggregate deserialize(String topic, byte[] data) {
            if (data == null) {
                return null;
            }
            try {
                var gson = new Gson();
                return gson.fromJson(new String(data, StandardCharsets.UTF_8), CoordinatesAggregate.class);
            } catch (Exception e) {
                throw new SerializationException(e);
            }
        }
    }
}
