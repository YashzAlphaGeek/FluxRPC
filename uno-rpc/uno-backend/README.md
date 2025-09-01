# UNO-RPC

**A turn-based multiplayer UNO game using gRPC for real-time gameplay**

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
    participant Player
    participant Server
    participant DB

    %% First player creates a game
    Player->>Server: joinGame(no gameId)
    Server->>DB: createGameSession
    DB-->>Server: GameSession saved
    Server-->>Player: JoinResponse(newPlayerIds=[P1], allPlayerIds=[P1])

    %% Subsequent players join
    loop Each new player
        Player->>Server: joinGame(with gameId)
        Server->>DB: addPlayer
        DB-->>Server: updated GameSession
        Server-->>Player: JoinResponse(newPlayerIds=[Pn], allPlayerIds=[P1..Pn])
    end

    %% Game state
    loop Subscribe
        Player->>Server: gameState
        Server-->>Player: GameStateResponse
    end

    %% Play cards
    loop Turns
        Player->>Server: playCard(card)
        Server->>DB: update GameSession
        DB-->>Server: updated GameSession
        Server-->>Player: PlayResponse + GameStateResponse
    end
```
---

## 1️⃣ Join Game

**Request**

```json
{
  "playerNames": ["Yash","Chuckaboo"]
}
```

**Response**

```json
{
  "allPlayerIds": [
    "eeec815f-55df-42bf-93db-bf1866c07b87",
    "37902f37-eceb-44f2-9a49-a506d53502d6"
  ],
  "newPlayerIds": [
    "37902f37-eceb-44f2-9a49-a506d53502d6"
  ],
  "message": "Game created and players joined",
  "gameId": "524d343f-8b96-483a-b9ab-5e2d29f6088d"
}
```

---

## 2️⃣ Play Card

### First Player Plays

**Request**

```json
{
  "gameId": "524d343f-8b96-483a-b9ab-5e2d29f6088d",
  "playerId": "eeec815f-55df-42bf-93db-bf1866c07b87",
  "card": "GREEN_7"
}
```

**Response**

```json
{
  "message": "eeec815f-55df-42bf-93db-bf1866c07b87 played GREEN_7",
  "nextPlayerId": "37902f37-eceb-44f2-9a49-a506d53502d6",
  "status": "OK"
}
```

---

### Second Player Plays

**Request**

```json
{
  "gameId": "524d343f-8b96-483a-b9ab-5e2d29f6088d",
  "playerId": "37902f37-eceb-44f2-9a49-a506d53502d6",
  "card": "RED_7"
}
```

**Response**

```json
{
  "message": "37902f37-eceb-44f2-9a49-a506d53502d6 played RED_7",
  "nextPlayerId": "eeec815f-55df-42bf-93db-bf1866c07b87",
  "status": "OK"
}
```

---

## 3️⃣ Game State

**Request**

```json
{
  "gameId": "524d343f-8b96-483a-b9ab-5e2d29f6088d"
}
```

**Response**

```json
{
  "players": [
    "eeec815f-55df-42bf-93db-bf1866c07b87",
    "37902f37-eceb-44f2-9a49-a506d53502d6"
  ],
  "cardsOnTable": [
    "eeec815f-55df-42bf-93db-bf1866c07b87:GREEN_7",
    "37902f37-eceb-44f2-9a49-a506d53502d6:RED_7"
  ],
  "gameId": "524d343f-8b96-483a-b9ab-5e2d29f6088d",
  "currentPlayerId": "eeec815f-55df-42bf-93db-bf1866c07b87"
}
```

---

<img width="861" height="311" alt="image" src="https://github.com/user-attachments/assets/ed789b1e-d727-4943-bfcc-a78e1cb80aaf" />

With this flow you can:
- Create or join a game,
- Play cards in turn,
- Observe game state updates live through the `gameState` stream.

---

## Running

Run the helper script to automatically kill any running Java processes, build the project, and start the Spring Boot application:

```bash
./run-java-maven.sh
```

The reason for killing any running Java processes before building or running your project is to avoid the protoc locking issue.
protoc (the Protocol Buffers compiler) sometimes needs to generate or overwrite files during the build.

If a Java process from a previous run is still running, it may lock files that protoc wants to access.

This results in errors like "file is locked" or build failures. By killing any running Java processes first, I ensure protoc can run without conflicts.

---

