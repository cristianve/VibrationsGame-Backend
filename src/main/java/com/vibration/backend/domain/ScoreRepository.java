package com.vibration.backend.domain;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;

public interface ScoreRepository {

    void saveScore(User user, Score score);
    Score getScore(User user) throws GameException;
    void deleteRanking();
}
