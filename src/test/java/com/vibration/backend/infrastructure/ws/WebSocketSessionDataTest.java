package com.vibration.backend.infrastructure.ws;

import com.vibration.backend.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebSocketSessionDataTest {

    private final Map<String, Object> objectMap = new HashMap<>();

    private final WebSocketSessionData webSocketSessionData = new WebSocketSessionData();

    @Mock
    private WebSocketSession session;

    @Test
    void updateAndGetSocketState() {
        when(session.getAttributes()).thenReturn(objectMap);

        webSocketSessionData.updateSocketState(session, SocketState.CONNECTED);
        SocketState socketState = webSocketSessionData.getSocketState(session);

        assertNotNull(socketState);
        assertEquals(SocketState.CONNECTED, socketState);
    }

    @Test
    void updateAndGetSocketAction() {
        when(session.getAttributes()).thenReturn(objectMap);

        webSocketSessionData.updateSocketAction(session, SocketAction.IDLE);
        SocketAction socketAction = webSocketSessionData.getSocketAction(session);

        assertNotNull(socketAction);
        assertEquals(SocketAction.IDLE, socketAction);
    }

    @Test
    void updateGameId() {
        GameId gameId = new GameId(UUID.randomUUID());
        when(session.getAttributes()).thenReturn(objectMap);

        webSocketSessionData.updateGameId(session, gameId);
        GameId gameIdRetrieved = webSocketSessionData.getGameId(session);

        assertNotNull(gameId);
        assertEquals(gameIdRetrieved.getGameUuid(), gameId.getGameUuid());
    }

    @Test
    void updateUser() {
        User user = new User("UserName");
        when(session.getAttributes()).thenReturn(objectMap);

        webSocketSessionData.updateUser(session, user);
        User userRetrieved = webSocketSessionData.getUser(session);

        assertNotNull(userRetrieved);
        assertEquals(user.getUserName(), userRetrieved.getUserName());
    }

    @Test
    void updateUserAdmin() {
        UserAdmin userAdmin = new UserAdmin(Boolean.TRUE);
        when(session.getAttributes()).thenReturn(objectMap);

        webSocketSessionData.updateUserAdmin(session, userAdmin);
        UserAdmin userAdminRetrieved = webSocketSessionData.getUserAdmin(session);

        assertNotNull(userAdminRetrieved);
        assertEquals(userAdmin.getAdmin(), userAdminRetrieved.getAdmin());
    }
}