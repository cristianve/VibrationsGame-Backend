package com.vibration.backend.infrastructure.ws;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketOperations {

    private static final Logger log = LogManager.getLogger(WebSocketOperations.class);

    private WebSocketOperations() {
        //Default private constructor
    }

    public static void sendObjectMessage(WebSocketSession session, Object message) {
        sendObjectMessage(session, new Gson().toJson(message));
    }

    public static void sendObjectMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("Unable to send message due to {}", e.getMessage());
        }
    }
}
