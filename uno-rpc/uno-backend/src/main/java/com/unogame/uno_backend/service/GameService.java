package com.unogame.uno_backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.unogame.uno_backend.model.GameSession;
import com.unogame.uno_backend.model.Player;

@Service
public class GameService {

    private final Map<String, GameSession> sessions = new HashMap<>();

    /**
     * Creates a new game session and adds the first player.
     */
    public String createGame(Player player) {
        String gameId = UUID.randomUUID().toString();
        GameSession session = new GameSession(gameId, new ArrayList<>());
        session.addPlayer(player.getPlayerId());
        sessions.put(gameId, session);
        return gameId;
    }

    /**
     * Processes a player's card play and returns the next player's ID.
     */
    public String playCard(String playerId, String card) {
        for (GameSession session : sessions.values()) {
            if (session.hasPlayer(playerId)) {
                return session.playCard(playerId, card);
            }
        }
        return null;
    }

    /**
     * Returns a list of player IDs in the game session.
     */
    public List<String> getPlayersInGame(String gameId) {
        GameSession session = sessions.get(gameId);
        return session != null ? session.getPlayers() : Collections.emptyList();
    }

    /**
     * Returns the cards currently on the table for the game session.
     */
    public List<String> getCardsOnTable(String gameId) {
        GameSession session = sessions.get(gameId);
        return session != null ? session.getCardsOnTable() : Collections.emptyList();
    }

    /**
     * Returns the current player's ID for the game session.
     */
    public String getCurrentPlayerId(String gameId) {
        GameSession session = sessions.get(gameId);
        return session != null ? session.getCurrentPlayerId() : null;
    }
}
