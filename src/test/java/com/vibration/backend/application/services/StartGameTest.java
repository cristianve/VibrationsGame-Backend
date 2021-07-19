package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.dto.StartGameResponseBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.figures.SelectFigure;
import com.vibration.backend.domain.services.game.GetGame;
import com.vibration.backend.domain.services.game.SaveGame;
import com.vibration.backend.domain.services.playuserdata.SavePlayUserData;
import com.vibration.backend.domain.services.score.SaveScore;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import com.vibration.backend.domain.services.session.SearchSessionByGameId;
import com.vibration.backend.domain.services.socket.GetSocketGameId;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import com.vibration.backend.domain.services.socket.UpdateSocketAction;
import com.vibration.backend.domain.services.socket.UpdateSocketState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartGameTest {

    @Mock
    private UpdateSocketAttributesOperations updateSocketAttributesOperations;

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private SaveSocketSession saveSocketSession;

    @Mock
    private SearchSessionByGameId searchSessionByGameId;

    @Mock
    private SaveScore saveScore;

    @Mock
    private SelectFigure selectFigure;

    @Mock
    private SavePlayUserData savePlayUserData;

    @Mock
    private GetGame getGame;

    @Mock
    private SaveGame saveGame;

    @Mock
    private GetSocketGameId getSocketGameId;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private UpdateSocketState updateSocketState;

    @Mock
    private UpdateSocketAction updateSocketAction;

    @Mock
    private WebSocketSession session;

    @Mock
    private WebSocketSession socketSessionInList;

    @Mock
    private WebSocketSession socketSessionAdminInList;

    @Mock
    private GameId gameId;

    @Mock
    private Game game;

    @Mock
    private User user;

    @Mock
    private User adminUser;

    @Test
    void executeWaitingThrowsGameExceptionWhenGettingGame() throws GameException {
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(getGame.execute(gameId)).thenThrow(GameException.class);

        StartGame startGame = new StartGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                saveSocketSession, searchSessionByGameId, saveScore, selectFigure, savePlayUserData, getGame, saveGame);
        assertThrows(GameException.class, () -> startGame.executeWaiting(session));
    }


    @Test
    void executeWaiting() throws GameException {
        List<WebSocketSession> webSocketSessionList = new ArrayList<>();
        webSocketSessionList.add(socketSessionInList);

        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(getGame.execute(gameId)).thenReturn(game);
        doNothing().when(game).setPlaying(true);
        when(saveGame.execute(game)).thenReturn(gameId);
        when(searchSessionByGameId.execute(gameId)).thenReturn(webSocketSessionList);
        when(updateSocketAttributesOperations.getUpdateSocketState()).thenReturn(updateSocketState);
        doNothing().when(updateSocketState).execute(socketSessionInList, SocketState.WAITING);
        when(updateSocketAttributesOperations.getUpdateSocketAction()).thenReturn(updateSocketAction);
        doNothing().when(updateSocketAction).execute(socketSessionInList, SocketAction.IDLE);
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(socketSessionInList)).thenReturn(user);
        doNothing().when(saveSocketSession).execute(user, socketSessionInList);

        StartGame startGame = new StartGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                saveSocketSession, searchSessionByGameId, saveScore, selectFigure, savePlayUserData, getGame, saveGame);
        Map<WebSocketSession, BasicResponseBody> responseBodyMap = startGame.executeWaiting(session);

        verify(saveSocketSession, times(1)).execute(user, socketSessionInList);

        assertNotNull(responseBodyMap);
        assertEquals(1, responseBodyMap.size());
    }

    @Test
    void executeStarting() {
        List<WebSocketSession> webSocketSessionList = new ArrayList<>();
        webSocketSessionList.add(socketSessionInList);
        webSocketSessionList.add(socketSessionAdminInList);

        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(searchSessionByGameId.execute(gameId)).thenReturn(webSocketSessionList);
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(adminUser);
        when(getSocketUser.execute(socketSessionInList)).thenReturn(user);
        when(getSocketUser.execute(socketSessionAdminInList)).thenReturn(adminUser);
        when(user.getUserName()).thenReturn("UserName");
        when(adminUser.getUserName()).thenReturn("AdminUserName");
        when(updateSocketAttributesOperations.getUpdateSocketState()).thenReturn(updateSocketState);
        doNothing().when(updateSocketState).execute(any(WebSocketSession.class), any(SocketState.class));
        when(updateSocketAttributesOperations.getUpdateSocketAction()).thenReturn(updateSocketAction);
        doNothing().when(updateSocketAction).execute(any(WebSocketSession.class), eq(SocketAction.IDLE));
        doNothing().when(saveSocketSession).execute(any(User.class), any(WebSocketSession.class));
        doNothing().when(saveScore).execute(any(User.class), any(Score.class));
        when(selectFigure.execute()).thenReturn(Figures.CIRCLE);
        doNothing().when(savePlayUserData).execute(any(PlayUserData.class));

        StartGame startGame = new StartGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                saveSocketSession, searchSessionByGameId, saveScore, selectFigure, savePlayUserData, getGame, saveGame);
        Map<WebSocketSession, StartGameResponseBody> responseBodyMap = startGame.executeStarting(session);

        assertNotNull(responseBodyMap);
        assertEquals(2, responseBodyMap.size());
    }
}