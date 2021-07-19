package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.CreateGameRequestBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.game.CheckGameNameExists;
import com.vibration.backend.domain.services.game.SaveGame;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

@Service
public class CreateGame {

    private final UpdateSocketAttributesOperations updateSocketAttributesOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final SaveGame saveGame;
    private final CheckGameNameExists checkGameNameExists;
    private final SaveSocketSession saveSocketSession;

    public CreateGame(UpdateSocketAttributesOperations updateSocketAttributesOperations,
                      GetSocketAttributesOperations getSocketAttributesOperations,
                      SaveGame saveGame,
                      CheckGameNameExists checkGameNameExists,
                      SaveSocketSession saveSocketSession) {
        this.updateSocketAttributesOperations = updateSocketAttributesOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.saveGame = saveGame;
        this.checkGameNameExists = checkGameNameExists;
        this.saveSocketSession = saveSocketSession;
    }

    public GameId execute(WebSocketSession session, CreateGameRequestBody createGameRequestBody)
            throws GameException {
        //Check request parameters
        checkRequestParameter(createGameRequestBody);

        //Build and save game data
        var game = buildGame(createGameRequestBody);
        if (checkGameNameExists.execute(game)) {
            throw new GameException("Game already exists");
        }
        var id = saveGame.execute(game);

        //Update and save socket data
        updateSocketAttributesOperations.getUpdateSocketGameId().execute(session, id);
        updateSocketAttributesOperations.getUpdateSocketUserAdmin().execute(session, new UserAdmin(Boolean.TRUE));
        updateSocketAttributesOperations.getUpdateSocketState().execute(session, SocketState.CREATED);
        updateSocketAttributesOperations.getUpdateSocketAction().execute(session, SocketAction.IDLE);

        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        saveSocketSession.execute(user, session);

        return id;
    }

    private void checkRequestParameter(CreateGameRequestBody createGameRequestBody) throws GameException {
        if (Objects.isNull(createGameRequestBody.getName()) || createGameRequestBody.getName().isBlank()) {
            throw new GameException("Invalid game name");
        }
    }

    private Game buildGame(CreateGameRequestBody createGameRequestBody) {
        var game = new Game(createGameRequestBody.getName());

        if (Objects.nonNull(createGameRequestBody.getMaxPlayers())) {
            game.setMaxPlayers(createGameRequestBody.getMaxPlayers());
        }

        if (Objects.nonNull(createGameRequestBody.getVictoryCondition())) {
            game.setVictoryCondition(createGameRequestBody.getVictoryCondition());
        }

        if (Objects.nonNull(createGameRequestBody.getCondition())) {
            if (VictoryConditions.TIME.equals(game.getVictoryCondition())) {
                //From seconds to milliseconds
                game.setCondition(createGameRequestBody.getCondition() * 1000);
            }
            else {
                game.setCondition(createGameRequestBody.getCondition());
            }
        }


        return game;
    }
}
