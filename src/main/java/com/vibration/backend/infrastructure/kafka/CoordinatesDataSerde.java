package com.vibration.backend.infrastructure.kafka;

import com.google.gson.Gson;
import com.vibration.backend.domain.model.CoordinatesData;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class CoordinatesDataSerde extends Serdes.WrapperSerde<CoordinatesData> {

    public CoordinatesDataSerde() {
        super(new CoordinatesDataSerializer(), new CoordinatesDataDeserializer());
    }

    static class CoordinatesDataSerializer implements Serializer<CoordinatesData> {

        @Override
        public byte[] serialize(String topic, CoordinatesData data) {
            return data.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    static class CoordinatesDataDeserializer implements Deserializer<CoordinatesData> {

        @Override
        public CoordinatesData deserialize(String topic, byte[] data) {
            if (data == null) {
                return null;
            }
            try {
                var gson = new Gson();
                return gson.fromJson(new String(data, StandardCharsets.UTF_8), CoordinatesData.class);
            } catch (Exception e) {
                throw new SerializationException(e);
            }
        }
    }
}
