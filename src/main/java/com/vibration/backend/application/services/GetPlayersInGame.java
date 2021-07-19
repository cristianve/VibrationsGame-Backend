package com.vibration.backend.application.services;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.game.ListPlayersInGame;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetPlayersInGame {

    private final ListPlayersInGame listPlayersInGame;

    public GetPlayersInGame(ListPlayersInGame listPlayersInGame) {
        this.listPlayersInGame = listPlayersInGame;
    }

    public List<User> execute(UUID gameUuid) throws GameException {
        var gameId = new GameId(gameUuid);
        return listPlayersInGame.execute(gameId);
    }
}
