package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class RemovePlayerFromGame {

    private final GameRepository gameRepository;

    public RemovePlayerFromGame(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void execute(GameId gameId, User user) throws GameException {
        gameRepository.removePlayer(gameId, user);
    }
}
