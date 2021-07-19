package com.vibration.backend.domain.services.ranking;

import com.vibration.backend.domain.ScoreRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteRanking {

    private final ScoreRepository scoreRepository;

    public DeleteRanking(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public void execute() {
        scoreRepository.deleteRanking();
    }
}
