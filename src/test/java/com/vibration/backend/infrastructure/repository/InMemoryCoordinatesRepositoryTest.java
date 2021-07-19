package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InMemoryCoordinatesRepositoryTest {

    private final User user = new User("UserName");
    private final CoordinatesData coordinatesData1 = new CoordinatesData(10L, 2.3f, 2.5f, 3.2f);
    private final CoordinatesData coordinatesData2 = new CoordinatesData(20L, 1.3f, 1.5f, 2.2f);

    private final InMemoryCoordinatesRepository coordinatesRepository = new InMemoryCoordinatesRepository();

    @Test
    @Order(1)
    void saveAndGet() {
        coordinatesRepository.save(user, coordinatesData1);
        coordinatesRepository.save(user, coordinatesData2);

        Queue<CoordinatesData> coordinatesDataQueue = coordinatesRepository.get(user);
        CoordinatesData poll1 = coordinatesDataQueue.poll();
        CoordinatesData poll2 = coordinatesDataQueue.poll();

        assertNotNull(coordinatesDataQueue);
        assertNotNull(poll1);
        assertNotNull(poll2);
        assertEquals(coordinatesData1, poll1);
        assertEquals(coordinatesData2, poll2);
    }

    @Test
    @Order(2)
    void delete() {
        coordinatesRepository.save(user, coordinatesData1);
        coordinatesRepository.delete(user);
        Queue<CoordinatesData> coordinatesData = coordinatesRepository.get(user);

        assertNull(coordinatesData);
    }
}