package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryGameRepository implements GameRepository {

    private static final String GAME_NOT_FOUND = "Game not found";

    private static final Map<UUID, Game> gamesMap = new ConcurrentHashMap<>();

    @Override
    public GameId saveGame(Game game) {
        gamesMap.put(game.getGameUuid(), game);
        return game.getId();
    }

    @Override
    public void deleteGame(GameId id) {
        gamesMap.remove(id.getGameUuid());
    }

    @Override
    public Game getGame(GameId id) throws GameException {
        var game = gamesMap.get(id.getGameUuid());
        if (game == null) {
            throw new GameException(GAME_NOT_FOUND);
        }

        return gamesMap.get(id.getGameUuid());
    }

    @Override
    public boolean checkGameNameExists(String name) {
        return gamesMap.values().stream().anyMatch(game -> game.getName().equals(name));
    }

    @Override
    public void addPlayer(GameId id, User user) throws GameException {
        var game = gamesMap.get(id.getGameUuid());
        if (game == null) {
            throw new GameException(GAME_NOT_FOUND);
        }

        game.addPlayer(user);
    }

    @Override
    public void removePlayer(GameId id, User user) throws GameException {
        var game = gamesMap.get(id.getGameUuid());
        if (game == null) {
            throw new GameException(GAME_NOT_FOUND);
        }

        game.removePlayer(user);
    }

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(gamesMap.values());
    }

    @Override
    public List<User> getPlayersInGame(GameId id) throws GameException {
        var game = gamesMap.get(id.getGameUuid());
        if (game == null) {
            throw new GameException(GAME_NOT_FOUND);
        }

        return game.getCurrentPlayers();
    }
}
