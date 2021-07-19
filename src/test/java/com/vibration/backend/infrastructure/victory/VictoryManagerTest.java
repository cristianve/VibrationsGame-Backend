package com.vibration.backend.infrastructure.victory;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.services.FinishGame;
import com.vibration.backend.application.services.GetGameInformation;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VictoryManagerTest {

    @Mock
    private GetGameInformation getGameInformation;

    @Mock
    private FinishGame finishGame;

    @Mock
    private TimeVictory timeVictory;

    @Mock
    private PointsVictory pointsVictory;

    @Mock
    private WebSocketSession session;

    @Mock
    private Game game;

    @Mock
    private Score score;

    @Test
    void executeTimeOK() throws GameException {
        Map<WebSocketSession, BasicResponseBody> responseBodyMap = new HashMap<>();
        responseBodyMap.put(session, new BasicResponseBody(0, "GAME ENDED", SocketState.END.toString(),
                SocketAction.IDLE.toString()));
        when(getGameInformation.execute(session)).thenReturn(game);
        when(game.getVictoryCondition()).thenReturn(VictoryConditions.TIME);
        when(game.getCondition()).thenReturn(10000);
        when(timeVictory.create(any(FinishGameCallable.class), eq(10000))).thenReturn(responseBodyMap);

        VictoryManager victoryManager = new VictoryManager(getGameInformation, finishGame, timeVictory, pointsVictory);
        Map<WebSocketSession, BasicResponseBody> map = victoryManager.executeTime(session);

        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(responseBodyMap, map);
    }

    @Test
    void executeTimeWhenVictoryConditionIsPoints() throws GameException {
        when(getGameInformation.execute(session)).thenReturn(game);
        when(game.getVictoryCondition()).thenReturn(VictoryConditions.POINTS);

        VictoryManager victoryManager = new VictoryManager(getGameInformation, finishGame, timeVictory, pointsVictory);
        Map<WebSocketSession, BasicResponseBody> map = victoryManager.executeTime(session);

        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    void executeTimeThrowsGameExceptionWhenGameNotFound() throws GameException {
        when(getGameInformation.execute(session)).thenThrow(GameException.class);

        VictoryManager victoryManager = new VictoryManager(getGameInformation, finishGame, timeVictory, pointsVictory);
        assertThrows(GameException.class, () -> victoryManager.executeTime(session));
    }

    @Test
    void executePointsOK() throws GameException {
        Map<WebSocketSession, BasicResponseBody> responseBodyMap = new HashMap<>();
        responseBodyMap.put(session, new BasicResponseBody(0, "GAME ENDED", SocketState.END.toString(),
                SocketAction.IDLE.toString()));
        when(getGameInformation.execute(session)).thenReturn(game);
        when(game.getVictoryCondition()).thenReturn(VictoryConditions.POINTS);
        when(pointsVictory.execute(session, score)).thenReturn(responseBodyMap);

        VictoryManager victoryManager = new VictoryManager(getGameInformation, finishGame, timeVictory, pointsVictory);
        Map<WebSocketSession, BasicResponseBody> map = victoryManager.executePoints(session, score);

        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(responseBodyMap, map);
    }

    @Test
    void executePointsWhenVictoryConditionIsTime() throws GameException {
        when(getGameInformation.execute(session)).thenReturn(game);
        when(game.getVictoryCondition()).thenReturn(VictoryConditions.TIME);

        VictoryManager victoryManager = new VictoryManager(getGameInformation, finishGame, timeVictory, pointsVictory);
        Map<WebSocketSession, BasicResponseBody> map = victoryManager.executePoints(session, score);

        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    void executePointsThrowsGameExceptionWhenGameNotFound() throws GameException {
        when(getGameInformation.execute(session)).thenThrow(GameException.class);

        VictoryManager victoryManager = new VictoryManager(getGameInformation, finishGame, timeVictory, pointsVictory);
        assertThrows(GameException.class, () -> victoryManager.executePoints(session, score));
    }
}