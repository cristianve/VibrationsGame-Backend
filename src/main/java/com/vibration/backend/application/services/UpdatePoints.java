package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.services.playuserdata.GetPlayUserData;
import com.vibration.backend.domain.services.score.CalculateScore;
import com.vibration.backend.domain.services.score.GetScore;
import com.vibration.backend.domain.services.score.SaveScore;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class UpdatePoints {

    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final GetPlayUserData getPlayUserData;
    private final CalculateScore calculateScore;
    private final SaveScore saveScore;
    private final GetScore getScore;

    public UpdatePoints(GetSocketAttributesOperations getSocketAttributesOperations,
                        GetPlayUserData getPlayUserData,
                        CalculateScore calculateScore,
                        SaveScore saveScore,
                        GetScore getScore) {
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.getPlayUserData = getPlayUserData;
        this.calculateScore = calculateScore;
        this.saveScore = saveScore;
        this.getScore = getScore;
    }

    public Score execute(WebSocketSession session) throws GameException {
        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        var playUserData = getPlayUserData.execute(user);
        Integer pointsObtained = calculateScore.execute(playUserData);
        var scoreSaved = getScore.execute(user);
        var scoreIncreased = scoreSaved.increaseScore(pointsObtained);
        saveScore.execute(user, scoreIncreased);
        return scoreIncreased;
    }
}
