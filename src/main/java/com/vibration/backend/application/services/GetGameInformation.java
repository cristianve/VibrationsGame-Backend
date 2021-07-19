package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.services.game.GetGame;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class GetGameInformation {

    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final GetGame getGame;

    public GetGameInformation(GetSocketAttributesOperations getSocketAttributesOperations,
                              GetGame getGame) {
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.getGame = getGame;
    }

    public Game execute(WebSocketSession session) throws GameException {
        var gameId = getSocketAttributesOperations.getGetSocketGameId().execute(session);
        return getGame.execute(gameId);
    }
}
