package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import org.springframework.stereotype.Component;

@Component
public class GetGame {

    private final GameRepository gameRepository;

    public GetGame(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game execute(GameId gameId) throws GameException {
        return gameRepository.getGame(gameId);
    }
}
