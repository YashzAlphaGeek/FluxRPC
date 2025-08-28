import { useState, useEffect } from "react";
import { unaryCall } from "./grpcClient";

function App() {
  const [unaryInput, setUnaryInput] = useState("");
  const [unaryResponse, setUnaryResponse] = useState("");

  const [streamCount, setStreamCount] = useState(5);
  const [streamResponses, setStreamResponses] = useState<string[]>([]);

  // Handle Unary RPC call
  const handleUnary = async () => {
    try {
      const res = await unaryCall(unaryInput);
      setUnaryResponse(res);
    } catch (err) {
      setUnaryResponse("Error: " + err);
    }
  };


  return (
    <div style={{ padding: "20px", fontFamily: "sans-serif" }}>
      <h1>FluxRPC gRPC-Web Demo</h1>

      {/* Unary RPC */}
      <section style={{ marginBottom: "30px" }}>
        <h2>Unary RPC</h2>
        <input
          type="text"
          value={unaryInput}
          onChange={(e) => setUnaryInput(e.target.value)}
          placeholder="Enter message"
        />
        <button onClick={handleUnary} style={{ marginLeft: "10px" }}>
          Send Unary Call
        </button>
        <p>Response: {unaryResponse}</p>
      </section>

    </div>
  );
}

export default App;
