package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.model.Figures;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InMemoryPlayRepositoryTest {

    private final InMemoryPlayRepository inMemoryPlayRepository = new InMemoryPlayRepository();

    private final User user = new User("UserName");
    private final Figures figure = Figures.CIRCLE;
    private final Instant instant = Instant.now();
    private final PlayUserData playUserData = new PlayUserData(user, figure, instant);

    @Test
    @Order(1)
    void saveAndGet() {
        inMemoryPlayRepository.save(playUserData);
        PlayUserData data = inMemoryPlayRepository.get(user);

        assertNotNull(data);
        assertEquals(playUserData, data);
    }

    @Test
    @Order(3)
    void delete() {
        inMemoryPlayRepository.delete(user);
        PlayUserData data = inMemoryPlayRepository.get(user);

        assertNull(data);
    }
}