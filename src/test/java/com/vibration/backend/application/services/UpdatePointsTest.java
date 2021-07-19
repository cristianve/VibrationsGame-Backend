package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.playuserdata.GetPlayUserData;
import com.vibration.backend.domain.services.score.CalculateScore;
import com.vibration.backend.domain.services.score.GetScore;
import com.vibration.backend.domain.services.score.SaveScore;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePointsTest {

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetPlayUserData getPlayUserData;

    @Mock
    private CalculateScore calculateScore;

    @Mock
    private SaveScore saveScore;

    @Mock
    private GetScore getScore;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Mock
    private PlayUserData playUserData;

    @Mock
    private Score score;

    @Mock
    private Score increasedScore;

    @Test
    void executeThrows() throws GameException {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getPlayUserData.execute(user)).thenReturn(playUserData);
        when(calculateScore.execute(playUserData)).thenReturn(10);
        when(getScore.execute(user)).thenThrow(GameException.class);

        UpdatePoints updatePoints = new UpdatePoints(getSocketAttributesOperations, getPlayUserData, calculateScore,
                saveScore, getScore);
        assertThrows(GameException.class, () -> updatePoints.execute(session));
    }

    @Test
    void executeOK() throws GameException {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getPlayUserData.execute(user)).thenReturn(playUserData);
        when(calculateScore.execute(playUserData)).thenReturn(10);
        when(getScore.execute(user)).thenReturn(score);
        when(score.increaseScore(10)).thenReturn(increasedScore);
        doNothing().when(saveScore).execute(user, increasedScore);

        UpdatePoints updatePoints = new UpdatePoints(getSocketAttributesOperations, getPlayUserData, calculateScore,
                saveScore, getScore);
        updatePoints.execute(session);

        verify(saveScore, times(1)).execute(user, increasedScore);
    }
}