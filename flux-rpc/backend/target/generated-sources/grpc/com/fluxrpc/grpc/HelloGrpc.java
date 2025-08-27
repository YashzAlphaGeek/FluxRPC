package com.fluxrpc.grpc;

import io.quarkus.grpc.MutinyService;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: hello.proto")
public interface HelloGrpc extends MutinyService {

    io.smallrye.mutiny.Uni<com.fluxrpc.grpc.HelloReply> sayHello(com.fluxrpc.grpc.HelloRequest request);
}
