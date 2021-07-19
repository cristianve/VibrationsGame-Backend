package com.vibration.backend.domain.services.score;

import com.vibration.backend.domain.ScoreRepository;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveScoreTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private User user;

    @Mock
    private Score score;

    @Test
    void execute() {
        doNothing().when(scoreRepository).saveScore(user, score);

        SaveScore saveScore = new SaveScore(scoreRepository);
        saveScore.execute(user, score);

        verify(scoreRepository, times(1)).saveScore(user, score);
    }
}