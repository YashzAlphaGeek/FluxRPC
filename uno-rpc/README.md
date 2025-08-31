# 🎲 UNO Game – gRPC with Envoy and React

**UNO card game** built using:

- **Backend**: Spring Boot gRPC server (game logic, player turns, validation)  
- **Proxy**: Envoy (handles gRPC-Web ↔ gRPC translation)  
- **Frontend**: React with `grpc-web` client  

The architecture allows **real-time communication** between players in the browser and the backend using **gRPC streaming**.

---

## High-Level Architecture

```mermaid
graph TD
    User[👨‍💻 Player in Browser] -->|UI Actions| Frontend[React Frontend<br/>gRPC-Web Client]
    Frontend -->|gRPC-Web| Envoy[Envoy Proxy<br/>grpc_web filter + CORS]
    Envoy -->|Native gRPC (HTTP/2)| Backend[Spring Boot UNO Service]
    Backend --> DB[(Game State Manager)]

    style User fill:#fdd,stroke:#333,stroke-width:1px
    style Frontend fill:#dff,stroke:#333,stroke-width:1px
    style Envoy fill:#ffd,stroke:#333,stroke-width:1px
    style Backend fill:#dfd,stroke:#333,stroke-width:1px
    style DB fill:#eee,stroke:#333,stroke-width:1px

##  Running the System

### 1. Backend (Spring Boot gRPC Server)
```bash
cd backend
./mvnw spring-boot:run
```

This starts the **UNO gRPC server** on port `9090` (configurable).

---

### 2. Envoy Proxy
Make sure you have an `envoy.yaml` in the project root.  
Run Envoy with Docker:

```bash
./run-envoy.sh
```

The script does:
```bash
docker run --rm   -p 8081:8080   -v "$PWD/envoy.yaml:/etc/envoy/envoy.yaml:ro"   envoyproxy/envoy:v1.30-latest
```

- **Envoy listens on port 8081** for gRPC-Web requests from the frontend.  
- Forwards them to the backend on port `9090`.  

---

### 3. Frontend (React App)
```bash
cd frontend
npm install
npm start
```

This starts the React app at [http://localhost:3000](http://localhost:3000).  
The React client talks to Envoy at `http://localhost:8081`.

---
