package com.vibration.backend.infrastructure.victory;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.services.FinishGame;
import com.vibration.backend.application.services.GetGameInformation;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Component
public class PointsVictory {

    private final GetGameInformation getGameInformation;
    private final FinishGame finishGame;

    public PointsVictory(GetGameInformation getGameInformation,
                         FinishGame finishGame) {
        this.getGameInformation = getGameInformation;
        this.finishGame = finishGame;
    }

    public Map<WebSocketSession, BasicResponseBody> execute(WebSocketSession session, Score score) throws GameException {
        var game = getGameInformation.execute(session);

        if (score.getPoints() < game.getCondition()) {
            return new HashMap<>();
        }

        return finishGame.execute(session);
    }
}
