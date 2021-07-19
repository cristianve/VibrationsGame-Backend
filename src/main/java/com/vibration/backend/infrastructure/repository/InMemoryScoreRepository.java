package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.ScoreRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryScoreRepository implements ScoreRepository {

    private static final Map<String, Integer> scoreMap = new ConcurrentHashMap<>();

    @Override
    public void saveScore(User user, Score score) {
        scoreMap.put(user.getUserName(), score.getPoints());
    }

    @Override
    public Score getScore(User user) throws GameException {
        var scoreInteger = scoreMap.get(user.getUserName());
        if (Objects.isNull(scoreInteger)) {
            throw new GameException("Score for user " + user.getUserName() + " not found");
        }
        return new Score(scoreInteger);
    }

    @Override
    public void deleteRanking() {
        scoreMap.clear();
    }
}
