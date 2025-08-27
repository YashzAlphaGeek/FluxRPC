import { DemoServiceClient } from '../grpc/StreamingDemoServiceClientPb';
import { 
  UnaryRequest,
  UnaryResponse,
  ServerStreamingRequest,
  ServerStreamingResponse,
  ClientStreamingRequest,
  ClientStreamingResponse,
  BiDiRequest,
  BiDiResponse
} from '../grpc/streaming_demo_pb';


// Create gRPC-Web client
const client = new DemoServiceClient('http://localhost:8080');

/**
 * Unary RPC call
 */
export const unaryCall = (message: string): Promise<string> => {
  return new Promise((resolve, reject) => {
    const req = new UnaryRequest();
    req.setMessage(message);

    client.unaryCall(req, {}, (err, resp: UnaryResponse) => {
      if (err) reject(err);
      else resolve(resp.getMessage());
    });
  });
};

/**
 * Server streaming RPC call
 * Returns an async iterator
 */
export const serverStreamingCall = (count: number): AsyncIterable<string> => {
  const req = new ServerStreamingRequest();
  req.setCount(count);

  const stream = client.serverStreamingCall(req, {});
  const iterator = {
    [Symbol.asyncIterator]: () => ({
      next: (): Promise<IteratorResult<string>> =>
        new Promise((resolve, reject) => {
          stream.on('data', (resp: ServerStreamingResponse) => {
            resolve({ value: resp.getMessage(), done: false });
          });
          stream.on('end', () => resolve({ value: undefined, done: true }));
          stream.on('error', (err: any) => reject(err));
        })
    })
  };

  return iterator;
};

/**
 * Client streaming RPC call
 */
export const clientStreamingCall = (messages: string[]): Promise<string> => {
  return new Promise((resolve, reject) => {
    const stream = client.clientStreamingCall((err, resp: ClientStreamingResponse) => {
      if (err) reject(err);
      else resolve(resp.getSummary());
    });

    messages.forEach(msg => {
      const req = new ClientStreamingRequest();
      req.setMessage(msg);
      stream.write(req);
    });

    stream.end();
  });
};

/**
 * Bidirectional streaming RPC call
 * Returns an async iterator for responses
 */
export const biDiStreamingCall = (messages: string[]): AsyncIterable<string> => {
  const stream = client.biDiStreamingCall();
  
  // send messages
  messages.forEach(msg => {
    const req = new BiDiRequest();
    req.setMessage(msg);
    stream.write(req);
  });
  stream.end();

  const iterator = {
    [Symbol.asyncIterator]: () => ({
      next: (): Promise<IteratorResult<string>> =>
        new Promise((resolve, reject) => {
          stream.on('data', (resp: BiDiResponse) => {
            resolve({ value: resp.getMessage(), done: false });
          });
          stream.on('end', () => resolve({ value: undefined, done: true }));
          stream.on('error', (err: any) => reject(err));
        })
    })
  };

  return iterator;
};
