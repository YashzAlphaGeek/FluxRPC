# FluxRPC Backend

This is the backend implementation of the **FluxRPC** project, using **gRPC** and **Quarkus** for serving RPC services.

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Running the Backend](#running-the-backend)
- [gRPC Services](#grpc-services)
- [Protobuf Definitions](#protobuf-definitions)
- [Generating gRPC Code for Frontend](#generating-grpc-code-for-frontend)
- [Notes on gRPC Streaming Types](#notes-on-grpc-streaming-types)
- [References](#references)

---

## Prerequisites

Make sure you have the following installed:

- **Java 17+**
- **Maven 3+**
- **Docker** (optional, for running `protoc` in a container)
- **Protoc Compiler** (for generating gRPC TypeScript files for frontend)

---

## Project Structure

```
backend/
├─ src/
│  ├─ main/
│  │  ├─ java/com/fluxrpc/grpc/
│  │  │  └─ DemoServiceImpl.java        # gRPC service implementation
│  │  ├─ proto/
│  │  │  └─ streaming_demo.proto        # Protobuf definitions
├─ pom.xml                              # Maven project configuration
```

---

## Running the Backend

### Using Maven

```bash
cd backend
mvn quarkus:dev
```

This will start the gRPC server on the default port **8080**.

---

## gRPC Services

The backend provides the following gRPC services:

### DemoService

| RPC Method             | Type                    | Description                                                  |
|------------------------|------------------------|--------------------------------------------------------------|
| `UnaryCall`            | Unary                  | Receives a single request and returns a single response.     |
| `ServerStreamingCall`  | Server streaming       | Sends multiple responses for a single request.             |
| `ClientStreamingCall`  | Client streaming       | Accepts a stream of requests and returns a single response. |
| `BiDiStreamingCall`    | Bidirectional streaming| Streams both requests and responses concurrently.           |

---

## Protobuf Definitions

Located in `src/main/proto/streaming_demo.proto`.

```proto
syntax = "proto3";

service DemoService {
    rpc UnaryCall (UnaryRequest) returns (UnaryResponse);
    rpc ServerStreamingCall (ServerStreamingRequest) returns (stream ServerStreamingResponse);
    rpc ClientStreamingCall (stream ClientStreamingRequest) returns (ClientStreamingResponse);
    rpc BiDiStreamingCall (stream BiDiRequest) returns (stream BiDiResponse);
}

message UnaryRequest {
    string message = 1;
}

message UnaryResponse {
    string message = 1;
}

message ServerStreamingRequest {
    int32 count = 1;
}

message ServerStreamingResponse {
    string message = 1;
}

message ClientStreamingRequest {
    string message = 1;
}

message ClientStreamingResponse {
    string summary = 1;
}

message BiDiRequest {
    string message = 1;
}

message BiDiResponse {
    string message = 1;
}
```

---

## Generating gRPC Code for Frontend

Use the `protoc` compiler or a Docker container to generate TypeScript gRPC client code for your frontend.

Example command:

```bash
protoc -I=src/main/proto \
  --ts_out=frontend/src/grpc \
  --grpc-web_out=import_style=typescript,mode=grpcwebtext:frontend/src/grpc \
  src/main/proto/*.proto
```

> This will generate TypeScript files you can import into your frontend project.

---

## Notes on gRPC Streaming Types

| Type                     | Direction                          | Description                                                                 |
|--------------------------|-----------------------------------|-----------------------------------------------------------------------------|
| Unary                    | Client → Server                   | Single request, single response                                             |
| Server Streaming         | Client → Server                   | Single request, multiple responses                                          |
| Client Streaming         | Client → Server                   | Multiple requests, single response                                          |
| Bidirectional Streaming  | Client ↔ Server                   | Multiple requests and responses streamed concurrently                      |

---

![gPRC](https://github.com/user-attachments/assets/c8714546-fe00-4648-a32a-9bac28725c99)

## References

- [Quarkus gRPC Guide](https://quarkus.io/guides/grpc)
- [gRPC Official Documentation](https://grpc.io/docs/)
- [Protobuf Language Guide](https://developers.google.com/protocol-buffers/docs/proto3)

