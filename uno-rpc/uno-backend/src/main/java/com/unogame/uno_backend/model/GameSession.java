package com.unogame.uno_backend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class GameSession {

    @Id
    private String gameId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "game_session_players", joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "gameId"), inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "playerId"))
    private List<Player> players = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Card> cardsOnTable = new ArrayList<>();

    @Transient
    private Map<String, List<Card>> playerHands = new HashMap<>();

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

    public synchronized List<Card> getCardsOnTable() {
        return new ArrayList<>(cardsOnTable);
    }

    public synchronized String getCurrentPlayerId() {
        if (players.isEmpty())
            return null;
        return players.get(currentPlayerIndex).getPlayerId();
    }

    public synchronized List<Card> getPlayerHand(String playerId) {
        return playerHands.getOrDefault(playerId, new ArrayList<>());
    }

    public synchronized void addPlayer(Player player) {
        if (!hasPlayer(player.getPlayerId())) {
            players.add(player);
            dealInitialHand(player);
        }
    }

    public synchronized boolean hasPlayer(String playerId) {
        return players.stream().anyMatch(p -> p.getPlayerId().equals(playerId));
    }

    public synchronized boolean isFull(int maxPlayers) {
        return players.size() >= maxPlayers;
    }

    public synchronized int getPlayerCount() {
        return players.size();
    }

    public synchronized PlayResult playCard(String playerId, Card card) {
        if (!hasPlayer(playerId)) {
            return new PlayResult(null, PlayResult.Status.INVALID_PLAYER);
        }

        if (!playerId.equals(getCurrentPlayerId())) {
            return new PlayResult(getCurrentPlayerId(), PlayResult.Status.INVALID_TURN);
        }

        List<Card> hand = playerHands.get(playerId);
        if (hand == null || !hand.remove(card)) {
            return new PlayResult(getCurrentPlayerId(), PlayResult.Status.INVALID_PLAYER);
        }

        cardsOnTable.add(card); // store card directly
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        return new PlayResult(getCurrentPlayerId(), PlayResult.Status.OK);
    }

    private void dealInitialHand(Player player) {
        List<Card> deck = generateDeck();
        Collections.shuffle(deck, new Random());

        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 7 && !deck.isEmpty(); i++) {
            hand.add(deck.remove(0));
        }
        playerHands.put(player.getPlayerId(), hand);
    }

    private List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();
        String[] colors = { "Red", "Blue", "Green", "Yellow" };
        String[] values = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Reverse", "DrawTwo" };

        for (String color : colors) {
            for (String value : values) {
                deck.add(new Card(color, value));
                if (!value.equals("0"))
                    deck.add(new Card(color, value)); // 2 of each except 0
            }
        }

        for (int i = 0; i < 4; i++) {
            deck.add(new Card("Wild", "Wild"));
            deck.add(new Card("Wild", "DrawFour"));
        }

        return deck;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setCardsOnTable(List<Card> cardsOnTable) {
        this.cardsOnTable = cardsOnTable;
    }

    public void setPlayerHands(Map<String, List<Card>> playerHands) {
        this.playerHands = playerHands;
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
