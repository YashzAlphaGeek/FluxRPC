import * as jspb from 'google-protobuf'



export class UnaryRequest extends jspb.Message {
  getMessage(): string;
  setMessage(value: string): UnaryRequest;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnaryRequest.AsObject;
  static toObject(includeInstance: boolean, msg: UnaryRequest): UnaryRequest.AsObject;
  static serializeBinaryToWriter(message: UnaryRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): UnaryRequest;
  static deserializeBinaryFromReader(message: UnaryRequest, reader: jspb.BinaryReader): UnaryRequest;
}

export namespace UnaryRequest {
  export type AsObject = {
    message: string,
  }
}

export class UnaryResponse extends jspb.Message {
  getMessage(): string;
  setMessage(value: string): UnaryResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): UnaryResponse.AsObject;
  static toObject(includeInstance: boolean, msg: UnaryResponse): UnaryResponse.AsObject;
  static serializeBinaryToWriter(message: UnaryResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): UnaryResponse;
  static deserializeBinaryFromReader(message: UnaryResponse, reader: jspb.BinaryReader): UnaryResponse;
}

export namespace UnaryResponse {
  export type AsObject = {
    message: string,
  }
}

export class ServerStreamingRequest extends jspb.Message {
  getCount(): number;
  setCount(value: number): ServerStreamingRequest;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ServerStreamingRequest.AsObject;
  static toObject(includeInstance: boolean, msg: ServerStreamingRequest): ServerStreamingRequest.AsObject;
  static serializeBinaryToWriter(message: ServerStreamingRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ServerStreamingRequest;
  static deserializeBinaryFromReader(message: ServerStreamingRequest, reader: jspb.BinaryReader): ServerStreamingRequest;
}

export namespace ServerStreamingRequest {
  export type AsObject = {
    count: number,
  }
}

export class ServerStreamingResponse extends jspb.Message {
  getMessage(): string;
  setMessage(value: string): ServerStreamingResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ServerStreamingResponse.AsObject;
  static toObject(includeInstance: boolean, msg: ServerStreamingResponse): ServerStreamingResponse.AsObject;
  static serializeBinaryToWriter(message: ServerStreamingResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ServerStreamingResponse;
  static deserializeBinaryFromReader(message: ServerStreamingResponse, reader: jspb.BinaryReader): ServerStreamingResponse;
}

export namespace ServerStreamingResponse {
  export type AsObject = {
    message: string,
  }
}

export class ClientStreamingRequest extends jspb.Message {
  getMessage(): string;
  setMessage(value: string): ClientStreamingRequest;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ClientStreamingRequest.AsObject;
  static toObject(includeInstance: boolean, msg: ClientStreamingRequest): ClientStreamingRequest.AsObject;
  static serializeBinaryToWriter(message: ClientStreamingRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ClientStreamingRequest;
  static deserializeBinaryFromReader(message: ClientStreamingRequest, reader: jspb.BinaryReader): ClientStreamingRequest;
}

export namespace ClientStreamingRequest {
  export type AsObject = {
    message: string,
  }
}

export class ClientStreamingResponse extends jspb.Message {
  getSummary(): string;
  setSummary(value: string): ClientStreamingResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ClientStreamingResponse.AsObject;
  static toObject(includeInstance: boolean, msg: ClientStreamingResponse): ClientStreamingResponse.AsObject;
  static serializeBinaryToWriter(message: ClientStreamingResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ClientStreamingResponse;
  static deserializeBinaryFromReader(message: ClientStreamingResponse, reader: jspb.BinaryReader): ClientStreamingResponse;
}

export namespace ClientStreamingResponse {
  export type AsObject = {
    summary: string,
  }
}

export class BiDiRequest extends jspb.Message {
  getMessage(): string;
  setMessage(value: string): BiDiRequest;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): BiDiRequest.AsObject;
  static toObject(includeInstance: boolean, msg: BiDiRequest): BiDiRequest.AsObject;
  static serializeBinaryToWriter(message: BiDiRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): BiDiRequest;
  static deserializeBinaryFromReader(message: BiDiRequest, reader: jspb.BinaryReader): BiDiRequest;
}

export namespace BiDiRequest {
  export type AsObject = {
    message: string,
  }
}

export class BiDiResponse extends jspb.Message {
  getMessage(): string;
  setMessage(value: string): BiDiResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): BiDiResponse.AsObject;
  static toObject(includeInstance: boolean, msg: BiDiResponse): BiDiResponse.AsObject;
  static serializeBinaryToWriter(message: BiDiResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): BiDiResponse;
  static deserializeBinaryFromReader(message: BiDiResponse, reader: jspb.BinaryReader): BiDiResponse;
}

export namespace BiDiResponse {
  export type AsObject = {
    message: string,
  }
}

