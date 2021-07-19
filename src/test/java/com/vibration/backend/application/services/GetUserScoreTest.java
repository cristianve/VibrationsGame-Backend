package com.vibration.backend.application.services;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.score.GetScore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserScoreTest {

    @Mock
    private GetScore getScore;

    @Mock
    private Score score;

    @Test
    void executeThrowsGameException() throws GameException {
        when(getScore.execute(any(User.class))).thenThrow(GameException.class);

        String username = "UserName";
        GetUserScore getUserScore = new GetUserScore(getScore);
        assertThrows(GameException.class, () -> getUserScore.execute(username));
    }

    @Test
    void executeOK() throws GameException {
        when(getScore.execute(any(User.class))).thenReturn(score);

        String username = "UserName";
        GetUserScore getUserScore = new GetUserScore(getScore);
        Score score = getUserScore.execute(username);

        assertNotNull(score);
        assertEquals(this.score, score);
    }
}