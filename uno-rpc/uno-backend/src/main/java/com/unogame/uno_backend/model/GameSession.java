package com.unogame.uno_backend.model;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private final String gameId;
    private final List<String> players;
    private final List<String> cardsOnTable;
    private int currentPlayerIndex;

    public GameSession(String gameId) {
        this.gameId = gameId;
        this.players = new ArrayList<>();
        this.cardsOnTable = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    public GameSession(String gameId, List<String> players) {
        this.gameId = gameId;
        this.players = new ArrayList<>(players);
        this.cardsOnTable = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    public String getGameId() {
        return gameId;
    }

    public List<String> getPlayers() {
        return players;
    }

    public List<String> getCardsOnTable() {
        return cardsOnTable;
    }

    public String getCurrentPlayerId() {
        if (players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }

    public void addPlayer(String playerId) {
        if (!players.contains(playerId)) {
            players.add(playerId);
        }
    }

    public boolean hasPlayer(String playerId) {
        return players.contains(playerId);
    }

    public String playCard(String playerId, String card) {
        if (!playerId.equals(getCurrentPlayerId())) {
            // Not this player's turn
            return getCurrentPlayerId();
        }

        // Add card to the table
        cardsOnTable.add(playerId + ":" + card);

        // Move to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return getCurrentPlayerId();
    }
}
