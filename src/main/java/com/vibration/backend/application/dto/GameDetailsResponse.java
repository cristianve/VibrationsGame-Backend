package com.vibration.backend.application.dto;

import java.util.UUID;

public class GameDetailsResponse {

    private final UUID id;
    private final String name;
    private final boolean isPlaying;

    public GameDetailsResponse(UUID id, String name, boolean isPlaying) {
        this.id = id;
        this.name = name;
        this.isPlaying = isPlaying;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
