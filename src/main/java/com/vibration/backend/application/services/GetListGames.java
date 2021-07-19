package com.vibration.backend.application.services;

import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.services.game.ListGames;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListGames {

    private final ListGames listGames;

    public GetListGames(ListGames listGames) {
        this.listGames = listGames;
    }

    public List<Game> execute() {
        return listGames.execute();
    }
}
