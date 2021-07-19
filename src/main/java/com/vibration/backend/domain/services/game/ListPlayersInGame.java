package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListPlayersInGame {

    private final GameRepository gameRepository;

    public ListPlayersInGame(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<User> execute(GameId gameId) throws GameException {
        return gameRepository.getPlayersInGame(gameId);
    }
}
