package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import org.springframework.stereotype.Component;

@Component
public class SaveGame {

    private final GameRepository gameRepository;

    public SaveGame(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameId execute(Game game) {
        return gameRepository.saveGame(game);
    }
}
