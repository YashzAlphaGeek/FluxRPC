package com.unogame.uno_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unogame.uno_backend.model.Card;
import com.unogame.uno_backend.model.GameSession;
import com.unogame.uno_backend.model.Player;
import com.unogame.uno_backend.repository.GameSessionRepository;

@Service
public class GameService {

    private final GameSessionRepository gameSessionRepository;
    private static final int MAX_PLAYERS = 4;
    private static final String CHAR_POOL = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int GAME_ID_LENGTH = 5;
    private final Random random = new Random();

    public GameService(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    private String generateGameCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < GAME_ID_LENGTH; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return sb.toString();
    }

    @Transactional
    public String createGame(Player firstPlayer) {
        String gameId;
        int attempts = 0;
        do {
            gameId = generateGameCode();
            attempts++;
            if (attempts > 10) throw new IllegalStateException("Cannot generate unique game ID");
        } while (gameSessionRepository.existsById(gameId));

        GameSession game = new GameSession(gameId);
        game.addPlayer(firstPlayer);
        gameSessionRepository.save(game);
        return gameId;
    }

    @Transactional
    public JoinResponse joinPlayers(String gameId, List<Player> players) {
        GameSession game = gameSessionRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found: " + gameId));

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
                .collect(Collectors.toList());

        return new JoinResponse(allPlayers, newPlayers);
    }

    @Transactional(readOnly = true)
    public List<PlayerInfo> getPlayersInGame(String gameId) {
        return gameSessionRepository.findById(gameId)
                .map(g -> g.getPlayers().stream()
                        .map(p -> new PlayerInfo(p.getPlayerId(), p.getName()))
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public List<Card> getPlayerHand(String gameId, String playerId) {
        return gameSessionRepository.findById(gameId)
                .map(game -> game.getPlayerHand(playerId))
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public List<Card> getCardsOnTable(String gameId) {
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
    public GameSession.PlayResult playCard(String gameId, String playerId, Card card) {
        GameSession game = gameSessionRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found: " + gameId));

        GameSession.PlayResult result = game.playCard(playerId, card);
        gameSessionRepository.save(game);
        return result;
    }

    // --- DTOs ---
    public record PlayerInfo(String id, String name) {}
    public record JoinResponse(List<PlayerInfo> allPlayers, List<PlayerInfo> newPlayers) {}
}
