package com.vibration.backend.application.dto;

import java.io.Serializable;
import java.util.List;

public class PlayerListResponse implements Serializable {

    private static final long serialVersionUID = -3350777817215863524L;

    private final int number;
    private final List<PlayerDetailsResponse> playerList;

    public PlayerListResponse(List<PlayerDetailsResponse> playerList) {
        this.playerList = playerList;
        this.number = playerList.size();
    }

    public int getNumber() {
        return number;
    }

    public List<PlayerDetailsResponse> getPlayerList() {
        return playerList;
    }
}
