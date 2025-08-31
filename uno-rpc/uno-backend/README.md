# UNO-RPC

**A turn-based multiplayer UNO game using gRPC for real-time gameplay and optional Web3 logging.**

---

## Description

UNO-RPC with **gRPC** for high-performance, bi-directional communication between players and the game server

**Key Features (MVP):**
- **Join Game (Unary RPC):** Players join sessions with unique IDs.
- **Play Card (BiDi Streaming):** Players send moves in real-time; the server broadcasts updates to opponents.
- **Game State (Server Streaming):** Clients receive continuous updates about the game state (cards on table, current player, etc.).

---

## Tech Stack
- **Backend:** Spring Boot
- **Communication:** gRPC + Protocol Buffers
- **Database:** H2 (for MVP), PostgreSQL optional
- **Frontend:** React / Angular (optional, gRPC-Web)

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
        +gameState(GameStateRequest) : void
    }

    class GameService {
        +createGame(Player) : String
        +joinExistingGame(String, Player) : JoinResult
        +playCard(String, String, String) : PlayResult
        +getPlayersInGame(String) : List~String~
        +getCardsOnTable(String) : List~String~
        +getCurrentPlayerId(String) : String
    }

    class UserService {
        +registerUser(String) : Player
        +findPlayer(String) : Player
    }

    class GameSession {
        -String gameId
        -List~String~ players
        -List~String~ cardsOnTable
        -int currentPlayerIndex
        +addPlayer(String)
        +hasPlayer(String) : boolean
        +playCard(String, String) : PlayResult
        +getCurrentPlayerId() : String
    }

    UnoServiceImpl --> GameService : uses
    UnoServiceImpl --> UserService : uses
    GameService --> GameSession : manages
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

    %% Broadcast Game State after each play
    S1Play --> S1State
    S1State --> C3
```

---

## Sequence Diagram

```mermaid
sequenceDiagram
    participant Player1
    participant Player2
    participant Server
    participant DB

    %% Players joining
    loop Players Join Game
        Player->>Server: joinGame(JoinRequest)
        Server->>DB: createGame or find GameSession
        DB-->>Server: GameSession saved/found
        Server-->>Player: JoinResponse(gameId, playerId, players list)
    end

    %% Players subscribe to game state
    loop Subscribe to Game State
        Player->>Server: gameState(GameStateRequest)
        Server-->>Player: GameStateResponse(initial state)
    end

    %% Players play cards in turns
    loop Player Turn
        Player->>Server: playCard(PlayRequest(card))
        Server->>DB: update GameSession with card
        DB-->>Server: updated GameSession
        Server-->>Player: PlayResponse(result)
        Server-->>Player1: GameStateResponse(updated state)
        Server-->>Player2: GameStateResponse(updated state)
    end
```
---

## Running

```bash
mvn clean install
mvn spring-boot:run
```
---

