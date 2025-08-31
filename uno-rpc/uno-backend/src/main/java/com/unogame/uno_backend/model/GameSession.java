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
    private List<String> players = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> cardsOnTable = new ArrayList<>();

    private int currentPlayerIndex = 0;

    protected GameSession() {}

    public GameSession(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() { return gameId; }
    public List<String> getPlayers() { return players; }
    public List<String> getCardsOnTable() { return cardsOnTable; }

    public String getCurrentPlayerId() {
        if (players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }

    public void addPlayer(String playerId) {
        if (!players.contains(playerId)) players.add(playerId);
    }

    public boolean hasPlayer(String playerId) {
        return players.contains(playerId);
    }

    /**
     * Play a card. Returns a PlayResult indicating success, invalid turn, or other issues.
     */
    public PlayResult playCard(String playerId, String card) {
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

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public void setCardsOnTable(List<String> cardsOnTable) {
        this.cardsOnTable = cardsOnTable;
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
