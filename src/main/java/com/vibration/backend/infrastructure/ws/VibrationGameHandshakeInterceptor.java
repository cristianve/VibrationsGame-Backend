package com.vibration.backend.infrastructure.ws;

import com.vibration.backend.domain.services.user.CreateUsername;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class VibrationGameHandshakeInterceptor implements HandshakeInterceptor {

    private static final String ATTR_USERNAME = "USERNAME";

    private final CreateUsername createUsername;

    public VibrationGameHandshakeInterceptor(CreateUsername createUsername) {
        this.createUsername = createUsername;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
        var user = createUsername.getUsername();
        attributes.put(ATTR_USERNAME, user.getUserName());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        //Void implementation
    }
}
