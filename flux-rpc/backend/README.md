# FluxRPC Backend

This is the backend implementation of the FluxRPC project, using **gRPC** and **Quarkus** for serving RPC services.

## Table of Contents

- [Prerequisites](#prerequisites)  
- [Project Structure](#project-structure)  
- [Running the Backend](#running-the-backend)  
- [gRPC Services](#grpc-services)  
- [Protobuf Definitions](#protobuf-definitions)  
- [Generating gRPC Code for Frontend](#generating-grpc-code-for-frontend)  

---

## Prerequisites

Make sure you have the following installed:

- **Java 17+**  
- **Maven 3+**  
- **Docker** (optional, for running protoc in a container)  
- **Protoc Compiler** (for generating gRPC TypeScript files for frontend)  

---

## Project Structure

backend/
├─ src/
│ ├─ main/
│ │ ├─ java/com/fluxrpc/grpc/
│ │ │ ├─ DemoServiceImpl.java # gRPC service implementation
│ │ ├─ proto/
│ │ │ ├─ streaming_demo.proto # Protobuf definitions
├─ pom.xml # Maven project configuration


---

## Running the Backend

### Using Maven

```bash
cd backend
mvn quarkus:dev


This will start the gRPC server on the default port 8080

## gRPC Services

The backend provides the following gRPC services:

### DemoService

| RPC Method             | Type                     | Description                                                  |
|------------------------|--------------------------|--------------------------------------------------------------|
| `UnaryCall`            | Unary                    | Receives a single request and returns a single response.    |
| `ServerStreamingCall`  | Server streaming         | Sends multiple responses for a single request.             |
| `ClientStreamingCall`  | Client streaming         | Accepts a stream of requests and returns a single response.|
| `BiDiStreamingCall`    | Bidirectional streaming  | Streams both requests and responses concurrently.           |


## Protobuf Definitions

Located in `src/main/proto/streaming_demo.proto`.

Example snippet:

```proto
syntax = "proto3";

service DemoService {
    rpc UnaryCall (UnaryRequest) returns (UnaryResponse);
    rpc ServerStreamingCall (ServerStreamingRequest) returns (stream ServerStreamingResponse);
    rpc ClientStreamingCall (stream ClientStreamingRequest) returns (ClientStreamingResponse);
    rpc BiDiStreamingCall (stream BiDiRequest) returns (stream BiDiResponse);
}

message UnaryRequest { string message = 1; }
message UnaryResponse { string message = 1; }
message ServerStreamingRequest { int32 count = 1; }
message ServerStreamingResponse { string message = 1; }
message ClientStreamingRequest { string message = 1; }
message ClientStreamingResponse { string summary = 1; }
message BiDiRequest { string message = 1; }
message BiDiResponse { string message = 1; }
