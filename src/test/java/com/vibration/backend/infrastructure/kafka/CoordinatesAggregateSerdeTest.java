package com.vibration.backend.infrastructure.kafka;

import com.vibration.backend.domain.model.CoordinatesData;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoordinatesAggregateSerdeTest {

    private final List<CoordinatesData> coordinatesDataList = Stream.of(
            new CoordinatesData(10L, 10.3f, 10.5f, 10.3f),
            new CoordinatesData(12L, 15.1f, 15.3f, 15.5f)
    ).collect(Collectors.toList());

    private static byte[] jsonBA;

    @Test
    @Order(1)
    void serializer() {
        CoordinatesAggregate coordinatesAggregate = new CoordinatesAggregate().withCoordinatesList(coordinatesDataList);
        jsonBA = new CoordinatesAggregateSerde().serializer().serialize("", coordinatesAggregate);
        String json = new String(jsonBA, StandardCharsets.UTF_8);

        assertNotNull(json);
    }

    @Test
    @Order(2)
    void deserializer() {
        CoordinatesAggregate coordinatesAggregate = new CoordinatesAggregateSerde().deserializer().deserialize("", jsonBA);

        assertNotNull(coordinatesAggregate);
    }
}