# FluxRPC: A gRPC Kickstart

The flow of **gRPC** calls—Unary, Server Streaming, Client Streaming, and BiDi Streaming—through a server interceptor to the service implementation and back in a **Quarkus**-based backend.

```mermaid
%% gRPC Call Flow

flowchart TD
    subgraph Client
        A1[UnaryCall Request]
        A2[ServerStreamingCall Request]
        A3[ClientStreamingCall Stream]
        A4[BiDiStreamingCall Stream]
    end

    subgraph Interceptor
        I[GrpcInterceptor]
    end

    subgraph Server
        S1[DemoServiceImpl]
        S1U[UnaryCall Response]
        S1SS[ServerStreaming Responses]
        S1CS[ClientStreaming Summary]
        S1BD[BiDi Streaming Responses]
    end

    %% Unary
    A1 --> I --> S1 --> S1U --> I --> A1

    %% Server Streaming
    A2 --> I --> S1 --> S1SS --> I --> A2

    %% Client Streaming
    A3 --> I --> S1 --> S1CS --> I --> A3

    %% BiDi Streaming
    A4 --> I --> S1 --> S1BD --> I --> A4
```

# UNO-RPC: Multiplayer Turn-Based Game with gRPC

The flow of **gRPC** calls in UNO-RPC—Unary (Join Game), Server Streaming (Game State), and BiDi Streaming (Play Card)—through server interceptors in the **Spring Boot** backend services to the clients.

```mermaid
%% UNO-RPC gRPC Call Flow

flowchart TD
    subgraph Client
        C1[Join Game Request]
        C2[Play Card Stream]
        C3[Request Game State]
        C4[Start Game Request]
    end

    subgraph Server
        S1[UnoServiceImpl]
        S1Join[Join Response]
        S1Start[Start Game Response]
        S1Play[Play Response Stream]
        S1State[Game State Stream]
    end

    %% Join Game (Unary)
    C1 --> S1 --> S1Join --> C1

    %% Start Game (Unary)
    C4 --> S1 --> S1Start --> C4
    S1 --> S1State --> C3

    %% Play Card (BiDi Streaming)
    C2 --> S1 --> S1Play --> C2

    %% Game State (Server Streaming)
    C3 --> S1 --> S1State --> C3
```

# UNO-RPC: UNO Game gRPC Flow: Join, Play, and Real-Time Updates

![uno-gRPC](https://github.com/user-attachments/assets/f0b36ae5-e230-4246-887f-9f9d4692da12)


# UNO Game Flow: Browser → Envoy → Backend

```mermaid
%%{init: {"sequence": {"showSequenceNumbers": true, "actorMargin": 50, "messageMargin": 20}}}%%
sequenceDiagram
    autonumber
    participant Player as User (Player in Browser)
    participant Frontend as React App
    participant Envoy as Envoy Proxy
    participant Backend as Uno Game Service

    %% Step 1: Player joins game
    Player->>Frontend: Clicks "Join Game"
    Note right of Frontend: Prepare gRPC-Web request
    Frontend->>Envoy: Sends gRPC-Web call
    Note right of Envoy: Translate to native gRPC
    Envoy->>Backend: Forward gRPC request
    Note right of Backend: Process join game logic
    Backend-->>Envoy: Response (success, lobby state)
    Envoy-->>Frontend: Translate back to gRPC-Web
    Frontend-->>Player: Update UI (show lobby, hands empty)

    %% Step 1.5: Start game
    Player->>Frontend: Clicks "Start Game"
    Note right of Frontend: Prepare gRPC-Web request
    Frontend->>Envoy: Sends StartGame request
    Envoy->>Backend: Forward StartGame request
    Note right of Backend: Deal initial hands, update game state
    Backend-->>Envoy: Response (success, full game state with hands)
    Envoy-->>Frontend: Translate back to gRPC-Web
    Frontend-->>Player: Update UI (show full hands, current player)

    %% Step 2: Player plays a card
    Player->>Frontend: Plays a card
    Note right of Frontend: Validate locally
    Frontend->>Envoy: Sends gRPC-Web call
    Note right of Envoy: Forward to backend
    Envoy->>Backend: Validate card & update game state
    Note right of Backend: Update turn, notify next player
    Backend-->>Envoy: Response (card valid, next player)
    Envoy-->>Frontend: Translate to gRPC-Web
    Frontend-->>Player: Update UI (cards & turn)
    
    %% Notes for context
    Note over Backend: Central game logic & state management
    Note over Envoy: Acts as bridge between Browser and Backend
```
