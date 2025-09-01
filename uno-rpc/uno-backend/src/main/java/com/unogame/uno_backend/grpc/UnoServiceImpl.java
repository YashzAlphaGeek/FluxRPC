package com.unogame.uno_backend.grpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unogame.uno_backend.model.GameSession.PlayResult;
import com.unogame.uno_backend.model.Player;
import com.unogame.uno_backend.service.GameService;
import com.unogame.uno_backend.service.GameService.PlayerInfo;
import com.unogame.uno_backend.service.UserService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class UnoServiceImpl extends UnoServiceGrpc.UnoServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(UnoServiceImpl.class);

    private final GameService gameService;
    private final UserService userService;
    private final Map<String, List<StreamObserver<GameStateResponse>>> gameStateObservers = new ConcurrentHashMap<>();

    @Autowired
    public UnoServiceImpl(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public void joinGame(JoinRequest request, StreamObserver<JoinResponse> responseObserver) {
        List<String> playerNames = request.getPlayerNamesList();

        if (playerNames.isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("At least one player name must be provided")
                    .asRuntimeException());
            return;
        }

        String gameId;
        boolean newGame = false;

        if (request.getGameId() == null || request.getGameId().isEmpty()) {
            Player firstPlayer = userService.registerUser(playerNames.get(0));
            gameId = gameService.createGame(firstPlayer);
            newGame = true;
        } else {
            gameId = request.getGameId();
        }

        List<Player> playersToJoin = new ArrayList<>();
        for (String name : playerNames) {
            playersToJoin.add(userService.registerUser(name));
        }

        GameService.JoinResponse serviceResponse = gameService.joinPlayers(gameId, playersToJoin);

        List<com.unogame.uno_backend.grpc.PlayerInfo> allPlayersGrpc = serviceResponse.allPlayers().stream()
                .map(this::toGrpcPlayerInfo)
                .collect(Collectors.toList());

        List<com.unogame.uno_backend.grpc.PlayerInfo> newPlayersGrpc = serviceResponse.newPlayers().stream()
                .map(this::toGrpcPlayerInfo)
                .collect(Collectors.toList());

        JoinResponse grpcResponse = JoinResponse.newBuilder()
                .setGameId(gameId)
                .setMessage(newGame ? "Game created and players joined" : "Players joined successfully")
                .addAllAllPlayerIds(allPlayersGrpc)
                .addAllNewPlayerIds(newPlayersGrpc)
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();

        logger.info("Players {} joined game {}", playerNames, gameId);
    }

    @Override
    public StreamObserver<PlayRequest> playCard(StreamObserver<PlayResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(PlayRequest request) {
                if (request.getGameId().isEmpty() || request.getPlayerId().isEmpty() || request.getCard().isEmpty()) {
                    responseObserver.onError(Status.INVALID_ARGUMENT
                            .withDescription("Game ID, Player ID, and Card must be provided")
                            .asRuntimeException());
                    return;
                }

                // Play the card
                PlayResult result = gameService.playCard(request.getGameId(), request.getPlayerId(), request.getCard());
                PlayStatus status = mapStatus(result.getStatus());

                PlayResponse response = PlayResponse.newBuilder()
                        .setMessage(request.getPlayerId() + " played " + request.getCard())
                        .setNextPlayerId(result.getNextPlayerId() != null ? result.getNextPlayerId() : "")
                        .setStatus(status)
                        .build();

                responseObserver.onNext(response);

                // Broadcast updated game state including last move info
                broadcastGameState(request.getGameId(), status, request.getPlayerId(), request.getCard());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error in playCard stream", t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void gameState(GameStateRequest request, StreamObserver<GameStateResponse> responseObserver) {
        String gameId = request.getGameId();
        gameStateObservers.computeIfAbsent(gameId, k -> new CopyOnWriteArrayList<>()).add(responseObserver);
        // Default lastMoveStatus is OK and no last card for new subscribers
        sendGameStateToObserver(gameId, responseObserver, PlayStatus.OK, null, null);
    }

    private void sendGameStateToObserver(String gameId, StreamObserver<GameStateResponse> observer,
                                         PlayStatus lastMoveStatus, String lastPlayerId, String lastCard) {

        // Fetch structured players and cards
        List<PlayerInfo> players = gameService.getPlayersInGame(gameId);
        List<String> cards = gameService.getCardsOnTable(gameId);
        String currentPlayer = gameService.getCurrentPlayerId(gameId);

        List<com.unogame.uno_backend.grpc.PlayerInfo> playersGrpc = players.stream()
                .map(this::toGrpcPlayerInfo)
                .collect(Collectors.toList());

        GameStateResponse.Builder builder = GameStateResponse.newBuilder()
                .setGameId(gameId)
                .addAllPlayers(playersGrpc)
                .addAllCardsOnTable(cards)
                .setCurrentPlayerId(currentPlayer != null ? currentPlayer : "")
                .setLastMoveStatus(lastMoveStatus);

        observer.onNext(builder.build());
    }

    private void broadcastGameState(String gameId, PlayStatus lastMoveStatus, String lastPlayerId, String lastCard) {
        List<StreamObserver<GameStateResponse>> observers = gameStateObservers.getOrDefault(gameId, List.of());
        for (StreamObserver<GameStateResponse> observer : observers) {
            sendGameStateToObserver(gameId, observer, lastMoveStatus, lastPlayerId, lastCard);
        }
    }

    private com.unogame.uno_backend.grpc.PlayerInfo toGrpcPlayerInfo(PlayerInfo p) {
        return com.unogame.uno_backend.grpc.PlayerInfo.newBuilder()
                .setId(p.id())
                .setName(p.name())
                .build();
    }

    private PlayStatus mapStatus(PlayResult.Status status) {
        return switch (status) {
            case OK -> PlayStatus.OK;
            case INVALID_TURN -> PlayStatus.INVALID_TURN;
            case INVALID_PLAYER -> PlayStatus.INVALID_PLAYER;
            default -> PlayStatus.GAME_NOT_FOUND;
        };
    }
}
