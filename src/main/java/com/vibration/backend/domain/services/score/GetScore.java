package com.vibration.backend.domain.services.score;

import com.vibration.backend.domain.ScoreRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class GetScore {

    private final ScoreRepository scoreRepository;

    public GetScore(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Score execute(User user) throws GameException {
        return scoreRepository.getScore(user);
    }
}
