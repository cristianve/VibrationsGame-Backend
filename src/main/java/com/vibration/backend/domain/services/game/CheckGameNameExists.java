package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.model.Game;
import org.springframework.stereotype.Component;

@Component
public class CheckGameNameExists {

    private final GameRepository gameRepository;

    public CheckGameNameExists(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean execute(Game game) {
        return gameRepository.checkGameNameExists(game.getName());
    }
}
