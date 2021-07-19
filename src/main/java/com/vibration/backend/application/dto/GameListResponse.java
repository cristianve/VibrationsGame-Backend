package com.vibration.backend.application.dto;

import java.util.List;

public class GameListResponse {

    private final int number;
    private final List<GameDetailsResponse> gameList;

    public GameListResponse(List<GameDetailsResponse> gameList) {
        this.number = gameList.size();
        this.gameList = gameList;
    }

    public int getNumber() {
        return number;
    }

    public List<GameDetailsResponse> getGameList() {
        return gameList;
    }
}
