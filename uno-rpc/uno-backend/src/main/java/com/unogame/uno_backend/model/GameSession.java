package com.unogame.uno_backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class GameSession {

    @Id
    private String gameId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "game_session_players", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "gameId"), inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "playerId"))
    private List<Player> players = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> cardsOnTable = new ArrayList<>();

    private int currentPlayerIndex = 0;

    protected GameSession() {
    }

    public GameSession(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public synchronized List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public synchronized List<String> getCardsOnTable() {
        return new ArrayList<>(cardsOnTable);
    }

    public synchronized String getCurrentPlayerId() {
        if (players.isEmpty())
            return null;
        return players.get(currentPlayerIndex).getPlayerId();
    }

    public synchronized void addPlayer(Player player) {
        if (players.stream().noneMatch(p -> p.getPlayerId().equals(player.getPlayerId()))) {
            players.add(player);
        }
    }

    public synchronized boolean hasPlayer(String playerId) {
        return players.stream().anyMatch(p -> p.getPlayerId().equals(playerId));
    }

    public synchronized PlayResult playCard(String playerId, String card) {
        if (players.isEmpty() || !hasPlayer(playerId)) {
            return new PlayResult(null, PlayResult.Status.INVALID_PLAYER);
        }

        if (!playerId.equals(getCurrentPlayerId())) {
            return new PlayResult(getCurrentPlayerId(), PlayResult.Status.INVALID_TURN);
        }

        if (card == null || card.isEmpty()) {
            return new PlayResult(getCurrentPlayerId(), PlayResult.Status.INVALID_PLAYER);
        }

        cardsOnTable.add(card);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        return new PlayResult(getCurrentPlayerId(), PlayResult.Status.OK);
    }

    public synchronized int getPlayerCount() {
        return players.size();
    }

    public synchronized boolean isFull(int maxPlayers) {
        return players.size() >= maxPlayers;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setCardsOnTable(List<String> cardsOnTable) {
        this.cardsOnTable = cardsOnTable;
    }

    public static class PlayResult {
        public enum Status {
            OK, INVALID_TURN, INVALID_PLAYER
        }

        private final String nextPlayerId;
        private final Status status;

        public PlayResult(String nextPlayerId, Status status) {
            this.nextPlayerId = nextPlayerId;
            this.status = status;
        }

        public String getNextPlayerId() {
            return nextPlayerId;
        }

        public Status getStatus() {
            return status;
        }
    }
}
