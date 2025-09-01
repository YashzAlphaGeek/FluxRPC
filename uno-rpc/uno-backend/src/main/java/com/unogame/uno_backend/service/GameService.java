package com.unogame.uno_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unogame.uno_backend.model.GameSession;
import com.unogame.uno_backend.model.Player;
import com.unogame.uno_backend.repository.GameSessionRepository;

@Service
public class GameService {

    private final GameSessionRepository gameSessionRepository;
    private static final int MAX_PLAYERS = 4;

    public GameService(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @Transactional
    public String createGame(Player firstPlayer) {
        String gameId = UUID.randomUUID().toString();
        GameSession gameSession = new GameSession(gameId);
        gameSession.addPlayer(firstPlayer);
        gameSessionRepository.save(gameSession);
        return gameId;
    }

    @Transactional
    public JoinResponse joinPlayers(String gameId, List<Player> players) {
        Optional<GameSession> optGame = gameSessionRepository.findById(gameId);
        if (optGame.isEmpty()) {
            throw new IllegalArgumentException("Game not found: " + gameId);
        }

        GameSession game = optGame.get();
        List<PlayerInfo> newPlayers = new ArrayList<>();

        for (Player player : players) {
            if (!game.hasPlayer(player.getPlayerId()) && !game.isFull(MAX_PLAYERS)) {
                game.addPlayer(player);
                newPlayers.add(new PlayerInfo(player.getPlayerId(), player.getName()));
            }
        }

        gameSessionRepository.save(game);

        List<PlayerInfo> allPlayers = game.getPlayers().stream()
                .map(p -> new PlayerInfo(p.getPlayerId(), p.getName()))
                .toList();

        return new JoinResponse(allPlayers, newPlayers);
    }

    @Transactional(readOnly = true)
    public List<PlayerInfo> getPlayersInGame(String gameId) {
        return gameSessionRepository.findById(gameId)
                .map(g -> g.getPlayers().stream()
                        .map(p -> new PlayerInfo(p.getPlayerId(), p.getName()))
                        .toList())
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public List<String> getCardsOnTable(String gameId) {
        return gameSessionRepository.findById(gameId)
                .map(GameSession::getCardsOnTable)
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public String getCurrentPlayerId(String gameId) {
        return gameSessionRepository.findById(gameId)
                .map(GameSession::getCurrentPlayerId)
                .orElse(null);
    }

    @Transactional
    public GameSession.PlayResult playCard(String gameId, String playerId, String card) {
        GameSession game = gameSessionRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found: " + gameId));

        GameSession.PlayResult result = game.playCard(playerId, card);
        gameSessionRepository.save(game);
        return result;
    }

    public record PlayerInfo(String id, String name) {}
    public record JoinResponse(List<PlayerInfo> allPlayers, List<PlayerInfo> newPlayers) {}
}
