package com.vibration.backend.infrastructure.ws;

import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

public class WebSocketAttributes {

    private static final String ATTR_GAME_ID = "GAME_ID";
    private static final String ATTR_USERNAME = "USERNAME";
    private static final String ATTR_SOCKET_STATE = "SOCKET_STATE";

    private WebSocketAttributes() {
        //Default private constructor
    }

    public static UUID getGameId(WebSocketSession session) {
        return (UUID) session.getAttributes().get(ATTR_GAME_ID);
    }

    public static String getUsername(WebSocketSession session) {
        return (String) session.getAttributes().get(ATTR_USERNAME);
    }

    public static WebSocketState getWebSocketState(WebSocketSession session) {
        return (WebSocketState) session.getAttributes().get(ATTR_SOCKET_STATE);
    }

}
