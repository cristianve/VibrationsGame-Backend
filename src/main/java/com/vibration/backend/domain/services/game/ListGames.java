package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.model.Game;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListGames {

    private final GameRepository gameRepository;

    public ListGames(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> execute() {
        return gameRepository.getAllGames();
    }
}
