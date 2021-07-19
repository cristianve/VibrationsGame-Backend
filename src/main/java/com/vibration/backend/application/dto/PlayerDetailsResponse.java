package com.vibration.backend.application.dto;

import java.io.Serializable;

public class PlayerDetailsResponse implements Serializable {

    private static final long serialVersionUID = -2978727088738234846L;

    private final String username;

    public PlayerDetailsResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
