package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class InMemorySessionRepositoryTest {

    private final InMemorySessionRepository inMemorySessionStoreRepository = new InMemorySessionRepository();

    private final User user = new User("UserName");

    @Mock
    private WebSocketSession webSocketSession;

    @Test
    @Order(1)
    void saveAndGetSocketSession() {
        inMemorySessionStoreRepository.saveSocketSession(user, webSocketSession);
        WebSocketSession socketSession = inMemorySessionStoreRepository.getSocketSession(user);

        assertNotNull(socketSession);
        assertEquals(webSocketSession, socketSession);
    }

    @Test
    @Order(2)
    void deleteSocketSession() {
        inMemorySessionStoreRepository.deleteSocketSession(user);
        WebSocketSession socketSession = inMemorySessionStoreRepository.getSocketSession(user);

        assertNull(socketSession);
    }
}