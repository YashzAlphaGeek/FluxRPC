package com.unogame.uno_backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

@Entity
public class GameSession {

    @Id
    private String gameId;

    @ElementCollection(fetch = FetchType.EAGER)
    private final List<String> players = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private final List<String> cardsOnTable = new ArrayList<>();

    private int currentPlayerIndex = 0;

    protected GameSession() {}

    public GameSession(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public synchronized List<String> getPlayers() {
        return new ArrayList<>(players); 
    }

    public synchronized List<String> getCardsOnTable() {
        return new ArrayList<>(cardsOnTable);
    }

    public synchronized String getCurrentPlayerId() {
        if (players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }

    public synchronized void addPlayer(String playerId) {
        if (!players.contains(playerId)) {
            players.add(playerId);
        }
    }

    public synchronized boolean hasPlayer(String playerId) {
        return players.contains(playerId);
    }

    public synchronized PlayResult playCard(String playerId, String card) {
        if (!players.contains(playerId)) {
            return new PlayResult(null, PlayResult.Status.INVALID_PLAYER);
        }
        if (!playerId.equals(getCurrentPlayerId())) {
            return new PlayResult(getCurrentPlayerId(), PlayResult.Status.INVALID_TURN);
        }

        cardsOnTable.add(playerId + ":" + card);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return new PlayResult(getCurrentPlayerId(), PlayResult.Status.OK);
    }

    public synchronized int getPlayerCount() {
        return players.size();
    }

    public synchronized boolean isFull(int maxPlayers) {
        return players.size() >= maxPlayers;
    }

    public static class PlayResult {
        public enum Status { OK, INVALID_TURN, INVALID_PLAYER }
        private final String nextPlayerId;
        private final Status status;

        public PlayResult(String nextPlayerId, Status status) {
            this.nextPlayerId = nextPlayerId;
            this.status = status;
        }

        public String getNextPlayerId() { return nextPlayerId; }
        public Status getStatus() { return status; }
    }
}
