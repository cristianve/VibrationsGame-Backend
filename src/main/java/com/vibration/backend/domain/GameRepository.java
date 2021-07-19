package com.vibration.backend.domain;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;

import java.util.List;

public interface GameRepository {

    GameId saveGame(Game game);
    void deleteGame(GameId id);
    Game getGame(GameId id) throws GameException;
    boolean checkGameNameExists(String name);
    void addPlayer(GameId id, User user) throws GameException;
    void removePlayer(GameId id, User user) throws GameException;
    List<Game> getAllGames();
    List<User> getPlayersInGame(GameId gameId) throws GameException;
}
