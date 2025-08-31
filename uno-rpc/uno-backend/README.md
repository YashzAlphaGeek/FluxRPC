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
    participant Player(1..N)
    participant Server
    participant DB

    %% First player creates a game
    Player(1)->>Server: joinGame(JoinRequest - no gameId)
    Server->>DB: createGameSession(Player1)
    DB-->>Server: GameSession saved
    Server-->>Player(1): JoinResponse(gameId, newPlayerIds=[P1], allPlayerIds=[P1])

    %% Subsequent players join existing game
    loop For each Player(2..N)
        Player(2..N)->>Server: joinGame(JoinRequest with gameId)
        Server->>DB: find GameSession by gameId
        DB-->>Server: GameSession found
        Server->>DB: addPlayer(Player(2..N))
        DB-->>Server: updated GameSession
        Server-->>Player(2..N): JoinResponse(gameId, newPlayerIds=[Pn], allPlayerIds=[P1..Pn])
    end

    %% Players subscribe to game state
    loop Each Player(1..N)
        Player(1..N)->>Server: gameState(GameStateRequest)
        Server-->>Player(1..N): GameStateResponse(initial state)
    end

    %% Players play cards in turns
    loop Turns for Player(1..N)
        Player(1..N)->>Server: playCard(PlayRequest(card))
        Server->>DB: update GameSession with card
        DB-->>Server: updated GameSession
        Server-->>Player(1..N): PlayResponse(result)
        Server-->>Player(1..N): GameStateResponse(updated state for all)
    end
```
---

## Running

```bash
mvn clean install
mvn spring-boot:run
```
---

