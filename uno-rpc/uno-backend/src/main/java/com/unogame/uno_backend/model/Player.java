package com.unogame.uno_backend.model;


public class Player {
private final String playerId;
private final String name;


public Player(String playerId, String name) {
this.playerId = playerId;
this.name = name;
}


public String getPlayerId() { return playerId; }
public String getName() { return name; }
}