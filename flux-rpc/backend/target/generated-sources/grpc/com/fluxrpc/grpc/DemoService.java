package com.fluxrpc.grpc;

import io.quarkus.grpc.MutinyService;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: streaming_demo.proto")
public interface DemoService extends MutinyService {

    io.smallrye.mutiny.Uni<com.fluxrpc.grpc.UnaryResponse> unaryCall(com.fluxrpc.grpc.UnaryRequest request);

    io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ServerStreamingResponse> serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request);

    io.smallrye.mutiny.Uni<com.fluxrpc.grpc.ClientStreamingResponse> clientStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ClientStreamingRequest> request);

    io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiResponse> biDiStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiRequest> request);
}
