package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.model.GameId;
import org.springframework.stereotype.Component;

@Component
public class DeleteGame {

    private final GameRepository gameRepository;

    public DeleteGame(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void execute(GameId gameId) {
        gameRepository.deleteGame(gameId);
    }
}
