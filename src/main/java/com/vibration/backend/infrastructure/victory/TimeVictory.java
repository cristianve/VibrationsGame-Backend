package com.vibration.backend.infrastructure.victory;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.domain.exceptions.GameException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class TimeVictory {

    public Map<WebSocketSession, BasicResponseBody> create(FinishGameCallable finishGameCallable, int durationInMillis) throws GameException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<Map<WebSocketSession, BasicResponseBody>> future = executorService
                .schedule(finishGameCallable, durationInMillis, TimeUnit.MILLISECONDS);

        Map<WebSocketSession, BasicResponseBody> responseMap;
        try {
            responseMap = future.get();
        } catch (InterruptedException | ExecutionException e) {
            executorService.shutdown();
            throw new GameException(e.getMessage());
        }

        executorService.shutdown();

        return responseMap;
    }
}
