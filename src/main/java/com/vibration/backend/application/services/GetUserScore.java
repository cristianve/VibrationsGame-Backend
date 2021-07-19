package com.vibration.backend.application.services;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.score.GetScore;
import org.springframework.stereotype.Service;

@Service
public class GetUserScore {

    private final GetScore getScore;

    public GetUserScore(GetScore getScore) {
        this.getScore = getScore;
    }

    public Score execute(String username) throws GameException {
        var user = new User(username);
        return getScore.execute(user);
    }
}
