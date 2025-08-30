package com.unogame.uno_backend.grpc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unogame.uno_backend.model.Player;
import com.unogame.uno_backend.service.GameService;
import com.unogame.uno_backend.service.UserService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * gRPC implementation of the UnoService defined in uno.proto
 */
@GrpcService
public class UnoServiceImpl extends UnoServiceGrpc.UnoServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(UnoServiceImpl.class);

    private final GameService gameService;
    private final UserService userService;

    // Keeps track of connected players (in-memory for now)
    private final Map<String, Player> players = new ConcurrentHashMap<>();

    @Autowired
    public UnoServiceImpl(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    /**
     * Unary RPC - Player joins a game session.
     */
    @Override
    public void joinGame(JoinRequest request, StreamObserver<JoinResponse> responseObserver) {
        if (request.getPlayerName() == null || request.getPlayerName().isEmpty()) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Player name cannot be empty")
                            .asRuntimeException()
            );
            return;
        }

        Player player = userService.registerUser(request.getPlayerName());
        players.put(player.getPlayerId(), player);

        JoinResponse response = JoinResponse.newBuilder()
                .setMessage("Joined successfully!")
                .setPlayerId(player.getPlayerId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Player {} joined the game.", player.getName());
    }

    /**
     * BiDi Streaming RPC - Players play cards and server broadcasts next player turn.
     */
    @Override
    public StreamObserver<PlayRequest> playCard(StreamObserver<PlayResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(PlayRequest request) {
                if (request.getPlayerId().isEmpty() || request.getCard().isEmpty()) {
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("Player ID and card must be provided")
                                    .asRuntimeException()
                    );
                    return;
                }

                String nextPlayerId = gameService.playCard(request.getPlayerId(), request.getCard());

                PlayResponse response = PlayResponse.newBuilder()
                        .setMessage(request.getPlayerId() + " played " + request.getCard())
                        .setNextPlayerId(nextPlayerId == null ? "" : nextPlayerId)
                        .build();

                responseObserver.onNext(response);
                logger.info("Player {} played card {}. Next player: {}", request.getPlayerId(), request.getCard(), nextPlayerId);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in playCard stream", t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                logger.info("playCard stream completed.");
            }
        };
    }

    /**
     * Server Streaming RPC - Stream game state updates for a given game session.
     */
    @Override
    public void gameState(GameStateRequest request, StreamObserver<GameStateResponse> responseObserver) {
        if (request.getGameId() == null || request.getGameId().isEmpty()) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("Game ID cannot be empty")
                            .asRuntimeException()
            );
            return;
        }

        List<String> playersList = gameService.getPlayersInGame(request.getGameId());
        List<String> cardsOnTable = gameService.getCardsOnTable(request.getGameId());
        String currentPlayerId = gameService.getCurrentPlayerId(request.getGameId());

        GameStateResponse response = GameStateResponse.newBuilder()
                .setGameId(request.getGameId())
                .addAllPlayers(playersList)
                .addAllCardsOnTable(cardsOnTable)
                .setCurrentPlayerId(currentPlayerId == null ? "" : currentPlayerId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Game state streamed for game {}", request.getGameId());
    }

    public Map<String, Player> getPlayers() {
        return players;
    }
}
