package com.vibration.backend.infrastructure.controller;

import com.vibration.backend.application.dto.GameDetailsResponse;
import com.vibration.backend.application.dto.GameListResponse;
import com.vibration.backend.application.dto.PlayerDetailsResponse;
import com.vibration.backend.application.dto.PlayerListResponse;
import com.vibration.backend.application.services.GetListGames;
import com.vibration.backend.application.services.GetPlayersInGame;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {

    private static final Logger log = LogManager.getLogger(GameController.class);

    private final GetListGames getListGames;
    private final GetPlayersInGame getPlayersInGame;

    public GameController(GetListGames getListGames, GetPlayersInGame getPlayersInGame) {
        this.getListGames = getListGames;
        this.getPlayersInGame = getPlayersInGame;
    }

    @GetMapping("/list")
    @ResponseBody
    public GameListResponse getGameList() {
        List<Game> gameList = getListGames.execute();
        List<GameDetailsResponse> gameDetailsResponses = gameList.stream()
                .map(game -> new GameDetailsResponse(game.getGameUuid(), game.getName(), game.isPlaying()))
                .collect(Collectors.toList());
        return new GameListResponse(gameDetailsResponses);
    }

    @GetMapping("/players/{gameId}")
    @ResponseBody
    public PlayerListResponse getPlayersInGame(@PathVariable("gameId") UUID gameUUId) {
        List<User> userList;
        try {
            userList = getPlayersInGame.execute(gameUUId);
        } catch (GameException e) {
            log.error("Unable to get list of users in the game {} due to {}", gameUUId, e.getMessage());
            userList = new ArrayList<>();
        }

        List<PlayerDetailsResponse> detailsResponses = userList.stream()
                .map(user -> new PlayerDetailsResponse(user.getUserName()))
                .collect(Collectors.toList());
        return new PlayerListResponse(detailsResponses);
    }
}
