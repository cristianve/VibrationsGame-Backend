package com.vibration.backend.infrastructure.ws;

import com.vibration.backend.domain.services.user.CreateUsername;
import com.vibration.backend.infrastructure.controller.PlayController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final PlayController playController;
    private final CreateUsername createUsername;

    @Autowired
    public WebSocketConfig(PlayController playController, CreateUsername createUsername) {
        this.playController = playController;
        this.createUsername = createUsername;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(playController,  "/vibration/websocket")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new VibrationGameHandshakeInterceptor(createUsername));
    }

}
