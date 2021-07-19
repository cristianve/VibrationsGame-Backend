package com.vibration.backend.infrastructure.victory;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.services.FinishGame;
import com.vibration.backend.application.services.GetGameInformation;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.VictoryConditions;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Service
public class VictoryManager {

    private final GetGameInformation getGameInformation;
    private final FinishGame finishGame;
    private final TimeVictory timeVictory;
    private final PointsVictory pointsVictory;

    public VictoryManager(GetGameInformation getGameInformation, FinishGame finishGame, TimeVictory timeVictory, PointsVictory pointsVictory) {
        this.getGameInformation = getGameInformation;
        this.finishGame = finishGame;
        this.timeVictory = timeVictory;
        this.pointsVictory = pointsVictory;
    }

    public Map<WebSocketSession, BasicResponseBody> executeTime(WebSocketSession session) throws GameException {
        var game = getGameInformation.execute(session);
        VictoryConditions victoryCondition = game.getVictoryCondition();

        if (VictoryConditions.TIME.equals(victoryCondition)) {
            var finishGameCallable = new FinishGameCallable(session, finishGame);
            int durationInMillis = game.getCondition();
            return timeVictory.create(finishGameCallable, durationInMillis);
        }
        else {
            return new HashMap<>();
        }
    }

    public Map<WebSocketSession, BasicResponseBody> executePoints(WebSocketSession session, Score score) throws GameException {
        var game = getGameInformation.execute(session);
        VictoryConditions victoryCondition = game.getVictoryCondition();

        if (VictoryConditions.POINTS.equals(victoryCondition)) {
            return pointsVictory.execute(session, score);
        }
        else {
            return new HashMap<>();
        }
    }
}
