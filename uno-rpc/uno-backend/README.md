# UNO-RPC

**A turn-based multiplayer UNO game using gRPC for real-time gameplay and optional Web3 logging.**

---

## Description

UNO-RPC with **gRPC** for high-performance, bi-directional communication between players and the game server

**Key Features (MVP):**
- **Join Game (Unary RPC):** Players join sessions with unique IDs.
- **Play Card (BiDi Streaming):** Players send moves in real-time; the server broadcasts updates to opponents.
- **Game State (Server Streaming):** Clients receive continuous updates about the game state (cards on table, current player, etc.).
- **Web3 Logging (Optional):** Game outcomes can be recorded on blockchain for verifiable history.

---

## Tech Stack
- **Backend:** Spring Boot
- **Communication:** gRPC + Protocol Buffers
- **Database:** H2 (for MVP), PostgreSQL optional
- **Frontend:** React / Angular (optional, gRPC-Web)
- **Blockchain:** Ethereum/Polygon (optional Web3 integration)

---

## gRPC Services

```proto
service UnoService {
    rpc JoinGame (JoinRequest) returns (JoinResponse);
    rpc PlayCard (stream PlayRequest) returns (stream PlayResponse);
    rpc GameState (GameStateRequest) returns (stream GameStateResponse);
}
```

---

## Class Diagram (Backend MVP)

```mermaid
classDiagram
    class UnoServiceImpl {
        +joinGame(JoinRequest) : JoinResponse
        +playCard(StreamObserver<PlayResponse>) : StreamObserver<PlayRequest>
        +gameState(GameStateRequest) : StreamObserver<GameStateResponse>
    }

    class GameService {
        -Map<String, GameSession> sessions
        +createGame(playerId)
        +playCard(playerId, card)
        +getGameState(gameId)
    }

    class UserService {
        -Map<String, User> users
        +registerUser(name)
        +authenticateWallet(walletAddress)
    }

    class Web3Service {
        +logWinner(gameId, playerId)
        +verifyTransaction(txId)
    }

    UnoServiceImpl --> GameService : uses
    UnoServiceImpl --> UserService : uses
    UnoServiceImpl --> Web3Service : optional
```

---

## Architecture Overview

```mermaid
flowchart TD
    subgraph Client
        C1[Join Game Request]
        C2[Play Card Stream]
        C3[Request Game State]
    end

    subgraph Server
        S1[UnoServiceImpl]
        S1Join[Join Response]
        S1Play[Play Response Stream]
        S1State[Game State Stream]
    end

    %% Join Game (Unary)
    C1 --> S1 --> S1Join --> C1

    %% Play Card (BiDi Streaming)
    C2 --> S1 --> S1Play --> C2

    %% Game State (Server Streaming)
    C3 --> S1 --> S1State --> C3
```

---

## Running

```bash
./mvnw spring-boot:run
```
---

