package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.SocketAction;
import com.vibration.backend.domain.model.SocketState;
import com.vibration.backend.domain.services.game.GetGame;
import com.vibration.backend.domain.services.game.RemovePlayerFromGame;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class LeaveGame {

    private final UpdateSocketAttributesOperations updateSocketAttributesOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final GetGame getGame;
    private final RemovePlayerFromGame removePlayerFromGame;
    private final SaveSocketSession saveSocketSession;

    public LeaveGame(UpdateSocketAttributesOperations updateSocketAttributesOperations,
                     GetSocketAttributesOperations getSocketAttributesOperations,
                     GetGame getGame, RemovePlayerFromGame removePlayerFromGame, SaveSocketSession saveSocketSession) {
        this.updateSocketAttributesOperations = updateSocketAttributesOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.getGame = getGame;
        this.removePlayerFromGame = removePlayerFromGame;
        this.saveSocketSession = saveSocketSession;
    }

    public void execute(WebSocketSession session) throws GameException {
        var gameId = getSocketAttributesOperations.getGetSocketGameId().execute(session);
        var game = getGame.execute(gameId);
        if (game.isPlaying()) {
            throw new GameException("Game already started");
        }

        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        removePlayerFromGame.execute(gameId, user);

        updateSocketAttributesOperations.getUpdateSocketGameId().execute(session, null);
        updateSocketAttributesOperations.getUpdateSocketState().execute(session, SocketState.CONNECTED);
        updateSocketAttributesOperations.getUpdateSocketAction().execute(session, SocketAction.IDLE);

        saveSocketSession.execute(user, session);
    }
}
