package com.fluxrpc.grpc;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {

    @Override
    public void unaryCall(UnaryRequest request, StreamObserver<UnaryResponse> responseObserver) {
        UnaryResponse response = UnaryResponse.newBuilder()
                .setMessage("Unary Response: " + request.getMessage())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void serverStreamingCall(ServerStreamingRequest request, StreamObserver<ServerStreamingResponse> responseObserver) {
        for (int i = 1; i <= request.getCount(); i++) {
            responseObserver.onNext(ServerStreamingResponse.newBuilder()
                    .setMessage("Server Stream message " + i).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ClientStreamingRequest> clientStreamingCall(StreamObserver<ClientStreamingResponse> responseObserver) {
        return new StreamObserver<>() {
            StringBuilder sb = new StringBuilder();
            @Override
            public void onNext(ClientStreamingRequest value) { sb.append(value.getMessage()).append("; "); }
            @Override
            public void onError(Throwable t) {}
            @Override
            public void onCompleted() {
                responseObserver.onNext(ClientStreamingResponse.newBuilder()
                        .setSummary("Client Stream Summary: " + sb.toString()).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<BiDiRequest> biDiStreamingCall(StreamObserver<BiDiResponse> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(BiDiRequest value) {
                responseObserver.onNext(BiDiResponse.newBuilder()
                        .setMessage("Echo: " + value.getMessage()).build());
            }
            @Override
            public void onError(Throwable t) {}
            @Override
            public void onCompleted() { responseObserver.onCompleted(); }
        };
    }
}

