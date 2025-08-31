# FluxRPC: A gRPC Kickstart

The flow of gRPC calls—Unary, Server Streaming, Client Streaming, and BiDi Streaming—through a server interceptor to the service implementation and back.

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

The flow of gRPC calls in UNO-RPC—Unary (Join Game), Server Streaming (Game State), and BiDi Streaming (Play Card)—through the backend services to the clients.

```mermaid
sequenceDiagram
    participant Player as User (Player in Browser)
    participant Frontend as React App
    participant Envoy as Envoy Proxy
    participant Backend as Uno Game Service

    Player->>Frontend: Clicks "Join Game" / "Play Card"
    Frontend->>Envoy: Sends gRPC-Web call
    Envoy->>Backend: Forwards as native gRPC
    Backend-->>Envoy: Game logic response (next player, card validation, etc.)
    Envoy-->>Frontend: Translates to gRPC-Web
    Frontend-->>Player: Updates UI (turn, cards, state)

    Note over Backend: Central game logic & state management  
    Note over Envoy: Acts as bridge between Browser and Backend
```

```mermaid
%% UNO-RPC gRPC Call Flow

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
