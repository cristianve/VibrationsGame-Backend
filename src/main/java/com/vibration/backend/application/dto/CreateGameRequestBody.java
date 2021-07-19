package com.vibration.backend.application.dto;

import com.vibration.backend.domain.model.SocketAction;
import com.vibration.backend.domain.model.VictoryConditions;

import java.io.Serializable;

public class CreateGameRequestBody extends BasicRequestBody implements Serializable {

    private static final long serialVersionUID = 4889028296072091319L;

    private final String name;
    private final Integer maxPlayers;
    private final VictoryConditions victoryCondition;
    private final Integer condition;

    public CreateGameRequestBody(SocketAction action, String name, int maxPlayers, VictoryConditions victoryCondition, int condition) {
        super(action);
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.victoryCondition = victoryCondition;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public VictoryConditions getVictoryCondition() {
        return victoryCondition;
    }

    public Integer getCondition() {
        return condition;
    }
}
