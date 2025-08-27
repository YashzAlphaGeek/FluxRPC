package com.fluxrpc.grpc;

import java.util.function.BiFunction;
import io.quarkus.grpc.MutinyClient;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: streaming_demo.proto")
public class DemoServiceClient implements DemoService, MutinyClient<MutinyDemoServiceGrpc.MutinyDemoServiceStub> {

    private final MutinyDemoServiceGrpc.MutinyDemoServiceStub stub;

    public DemoServiceClient(String name, io.grpc.Channel channel, BiFunction<String, MutinyDemoServiceGrpc.MutinyDemoServiceStub, MutinyDemoServiceGrpc.MutinyDemoServiceStub> stubConfigurator) {
        this.stub = stubConfigurator.apply(name, MutinyDemoServiceGrpc.newMutinyStub(channel));
    }

    private DemoServiceClient(MutinyDemoServiceGrpc.MutinyDemoServiceStub stub) {
        this.stub = stub;
    }

    public DemoServiceClient newInstanceWithStub(MutinyDemoServiceGrpc.MutinyDemoServiceStub stub) {
        return new DemoServiceClient(stub);
    }

    @Override
    public MutinyDemoServiceGrpc.MutinyDemoServiceStub getStub() {
        return stub;
    }

    @Override
    public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.UnaryResponse> unaryCall(com.fluxrpc.grpc.UnaryRequest request) {
        return stub.unaryCall(request);
    }

    @Override
    public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ServerStreamingResponse> serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request) {
        return stub.serverStreamingCall(request);
    }

    @Override
    public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.ClientStreamingResponse> clientStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ClientStreamingRequest> request) {
        return stub.clientStreamingCall(request);
    }

    @Override
    public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiResponse> biDiStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiRequest> request) {
        return stub.biDiStreamingCall(request);
    }
}
