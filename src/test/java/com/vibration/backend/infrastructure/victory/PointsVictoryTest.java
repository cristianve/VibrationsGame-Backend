package com.vibration.backend.infrastructure.victory;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.services.FinishGame;
import com.vibration.backend.application.services.GetGameInformation;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.SocketAction;
import com.vibration.backend.domain.model.SocketState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointsVictoryTest {

    @Mock
    private GetGameInformation getGameInformation;

    @Mock
    private FinishGame finishGame;

    @Mock
    private WebSocketSession session;

    @Mock
    private Game game;

    @Test
    void executeReturnVoidMap() throws GameException {
        when(getGameInformation.execute(session)).thenReturn(game);
        when(game.getCondition()).thenReturn(100);

        PointsVictory pointsVictory = new PointsVictory(getGameInformation, finishGame);
        Map<WebSocketSession, BasicResponseBody> map = pointsVictory.execute(session, new Score(50));

        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    void executeReturnMap() throws GameException {
        Map<WebSocketSession, BasicResponseBody> responseBodyMap = new HashMap<>();
        responseBodyMap.put(session, new BasicResponseBody(0, "GAME ENDED", SocketState.END.toString(),
                        SocketAction.IDLE.toString()));
        when(getGameInformation.execute(session)).thenReturn(game);
        when(game.getCondition()).thenReturn(100);
        when(finishGame.execute(session)).thenReturn(responseBodyMap);

        PointsVictory pointsVictory = new PointsVictory(getGameInformation, finishGame);
        Map<WebSocketSession, BasicResponseBody> map = pointsVictory.execute(session, new Score(150));

        assertNotNull(map);
        assertEquals(1, map.size());
        verify(finishGame, times(1)).execute(session);
    }

    @Test
    void executeThrowsGameExceptionWhenGameNotFound() throws GameException {
        when(getGameInformation.execute(session)).thenThrow(GameException.class);

        PointsVictory pointsVictory = new PointsVictory(getGameInformation, finishGame);
        assertThrows(GameException.class, () -> pointsVictory.execute(session, new Score(150)));
    }
}