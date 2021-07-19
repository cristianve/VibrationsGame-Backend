package com.vibration.backend.domain.services.score;

import com.vibration.backend.domain.ScoreRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetScoreTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private User user;

    @Mock
    private Score score;

    @Test
    void executeThrowsGameException() throws GameException {
        when(scoreRepository.getScore(user)).thenThrow(GameException.class);

        GetScore getScore = new GetScore(scoreRepository);
        assertThrows(GameException.class, () -> getScore.execute(user));
    }

    @Test
    void executeOK() throws GameException {
        when(scoreRepository.getScore(user)).thenReturn(score);

        GetScore getScore = new GetScore(scoreRepository);
        Score score = getScore.execute(user);

        assertNotNull(score);
        assertEquals(this.score, score);
        verify(scoreRepository, times(1)).getScore(user);
    }
}