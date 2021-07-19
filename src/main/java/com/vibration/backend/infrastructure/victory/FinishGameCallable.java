package com.vibration.backend.infrastructure.victory;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.services.FinishGame;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.Callable;

public class FinishGameCallable implements Callable<Map<WebSocketSession, BasicResponseBody>> {

    private final WebSocketSession session;
    private final FinishGame finishGame;

    public FinishGameCallable(WebSocketSession session,FinishGame finishGame) {
        this.session = session;
        this.finishGame = finishGame;
    }

    @Override
    public Map<WebSocketSession, BasicResponseBody> call() {
        return finishGame.execute(session);
    }
}
