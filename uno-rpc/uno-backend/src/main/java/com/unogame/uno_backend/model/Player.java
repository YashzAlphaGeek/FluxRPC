package com.unogame.uno_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    private String playerId;

    private String name;

    protected Player() {
    }

    public Player(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
}
