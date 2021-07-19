package com.vibration.backend.domain.services.score;

import com.vibration.backend.domain.ScoreRepository;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class SaveScore {

    private final ScoreRepository scoreRepository;

    public SaveScore(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public void execute(User user, Score score) {
        scoreRepository.saveScore(user, score);
    }
}
