package com.unogame.uno_backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    private String playerId;

    private String name;

    @ElementCollection
    private List<String> hand = new ArrayList<>();

    protected Player() {
    }

    public Player(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public List<String> getHand() {
        return new ArrayList<>(hand);
    }

    public void setHand(List<String> hand) {
        this.hand = new ArrayList<>(hand);
    }

    public void addCard(String card) {
        this.hand.add(card);
    }

    public void removeCard(String card) {
        this.hand.remove(card);
    }
}
