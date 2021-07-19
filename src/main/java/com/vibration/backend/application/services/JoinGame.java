package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.AssignUserToGameRequestBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.SocketState;
import com.vibration.backend.domain.services.game.AssignPlayerToGame;
import com.vibration.backend.domain.services.game.GetGame;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class JoinGame {

    private final UpdateSocketAttributesOperations updateSocketAttributesOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final AssignPlayerToGame assignPlayerToGame;
    private final SaveSocketSession saveSocketSession;
    private final GetGame getGame;

    public JoinGame(UpdateSocketAttributesOperations updateSocketAttributesOperations,
                    GetSocketAttributesOperations getSocketAttributesOperations,
                    AssignPlayerToGame assignPlayerToGame,
                    SaveSocketSession saveSocketSession, GetGame getGame) {
        this.updateSocketAttributesOperations = updateSocketAttributesOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.assignPlayerToGame = assignPlayerToGame;
        this.saveSocketSession = saveSocketSession;
        this.getGame = getGame;
    }

    public void execute(WebSocketSession session, AssignUserToGameRequestBody requestBody) throws GameException {
        var gameId = new GameId(requestBody.getGameId());
        var game = getGame.execute(gameId);
        if (game.isPlaying()) {
            throw new GameException("Game already started");
        }

        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        assignPlayerToGame.execute(gameId, user);

        updateSocketAttributesOperations.getUpdateSocketGameId().execute(session, gameId);
        updateSocketAttributesOperations.getUpdateSocketState().execute(session, SocketState.JOINED);

        saveSocketSession.execute(user, session);
    }
}
