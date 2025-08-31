package com.unogame.uno_backend.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unogame.uno_backend.model.GameSession;
import com.unogame.uno_backend.model.GameSession.PlayResult;
import com.unogame.uno_backend.model.Player;
import com.unogame.uno_backend.repository.GameSessionRepository;

import jakarta.transaction.Transactional;

@Service
public class GameService {

    private final GameSessionRepository gameSessionRepository;


    @Autowired
    public GameService(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @Transactional
    public String createGame(Player player) {
        String gameId = UUID.randomUUID().toString();
        GameSession session = new GameSession(gameId);
        session.addPlayer(player.getPlayerId());
        gameSessionRepository.saveAndFlush(session);
        return gameId;
    }

    @Transactional
    public JoinResult joinExistingGame(String gameId, Player player) {
        GameSession session = gameSessionRepository.findById(gameId).orElse(null);
        if (session == null) return null;

        boolean alreadyJoined = session.hasPlayer(player.getPlayerId());
        if (!alreadyJoined) {
            session.addPlayer(player.getPlayerId());
            gameSessionRepository.saveAndFlush(session); 
        }

        return new JoinResult(session.getGameId(), alreadyJoined);
    }

    @Transactional
    public PlayResult playCard(String gameId, String playerId, String card) {
        GameSession session = gameSessionRepository.findById(gameId).orElse(null);
        if (session != null) {
            PlayResult result = session.playCard(playerId, card);
            gameSessionRepository.saveAndFlush(session); 
            return result;
        }
        return new PlayResult(null, PlayResult.Status.INVALID_PLAYER);
    }

    public List<String> getPlayersInGame(String gameId) {
        return gameSessionRepository.findById(gameId)
                .map(GameSession::getPlayers)
                .orElse(Collections.emptyList());
    }

    public List<String> getCardsOnTable(String gameId) {
        GameSession session = gameSessionRepository.findById(gameId).orElse(null);
        return session != null ? session.getCardsOnTable() : Collections.emptyList();

    }

    public String getCurrentPlayerId(String gameId) {
        return gameSessionRepository.findById(gameId)
                .map(GameSession::getCurrentPlayerId)
                .orElse(null);
    }

    public record JoinResult(String gameId, boolean alreadyJoined) {
    }

}
