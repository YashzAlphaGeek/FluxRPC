import * as grpcWeb from "grpc-web";
import { DemoServiceClient } from "./grpc/Streaming_demoServiceClientPb";
import * as proto from "./grpc/streaming_demo_pb";

// gRPC-Web client instance (backend running on port 8080)
const client = new DemoServiceClient("http://localhost:8080");

/** Unary RPC call */
export const unaryCall = (message: string): Promise<string> => {
  return new Promise((resolve, reject) => {
    const req = new proto.UnaryRequest();
    req.setMessage(message);

    client.unaryCall(req, {}, (err: grpcWeb.RpcError | null, resp: proto.UnaryResponse) => {
      if (err) reject(err);
      else resolve(resp.getMessage());
    });
  });
};