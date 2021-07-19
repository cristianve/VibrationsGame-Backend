package com.vibration.backend.domain.model;

import com.vibration.backend.domain.exceptions.GameException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    private final GameId id;
    private final String name;
    private boolean playing;
    private int maxPlayers;
    private final List<User> currentPlayers;
    private VictoryConditions victoryCondition;
    private int condition;

    public Game(String name) {
        id = GameId.create();
        this.name = name;
        playing = false;
        maxPlayers = 10;
        currentPlayers = new ArrayList<>();
        victoryCondition = VictoryConditions.TIME;
        condition = 60000;
    }

    public Game(String name, Integer maxPlayers, VictoryConditions victoryCondition, Integer condition) {
        id = GameId.create();
        playing = false;
        this.name = name;
        this.maxPlayers = maxPlayers;
        currentPlayers = new ArrayList<>();
        this.victoryCondition = victoryCondition;
        this.condition = condition;
    }

    public GameId getId() {
        return id;
    }

    public UUID getGameUuid() {
        return id.getGameUuid();
    }

    public String getName() {
        return name;
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<User> getCurrentPlayers() {
        return currentPlayers;
    }

    public VictoryConditions getVictoryCondition() {
        return victoryCondition;
    }

    public int getCondition() {
        return condition;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setVictoryCondition(VictoryConditions victoryCondition) {
        this.victoryCondition = victoryCondition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public synchronized void addPlayer(User user) throws GameException {
        if (currentPlayers.size() < maxPlayers){
            currentPlayers.add(user);
        }
        else {
            throw new GameException("Game already reached max number of players");
        }
    }

    public synchronized void removePlayer(User user) throws GameException {
        boolean remove = currentPlayers.remove(user);
        if (!remove) {
            throw new GameException("User not found in game");
        }
    }
}
