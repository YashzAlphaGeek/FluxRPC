package com.fluxrpc.grpc;

import io.grpc.BindableService;
import io.quarkus.grpc.GrpcService;
import io.quarkus.grpc.MutinyBean;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: streaming_demo.proto")
public class DemoServiceBean extends MutinyDemoServiceGrpc.DemoServiceImplBase implements BindableService, MutinyBean {

    private final DemoService delegate;

    DemoServiceBean(@GrpcService DemoService delegate) {
        this.delegate = delegate;
    }

    @Override
    public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.UnaryResponse> unaryCall(com.fluxrpc.grpc.UnaryRequest request) {
        try {
            return delegate.unaryCall(request);
        } catch (UnsupportedOperationException e) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }
    }

    @Override
    public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ServerStreamingResponse> serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request) {
        try {
            return delegate.serverStreamingCall(request);
        } catch (UnsupportedOperationException e) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }
    }

    @Override
    public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.ClientStreamingResponse> clientStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ClientStreamingRequest> request) {
        try {
            return delegate.clientStreamingCall(request);
        } catch (UnsupportedOperationException e) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }
    }

    @Override
    public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiResponse> biDiStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiRequest> request) {
        try {
            return delegate.biDiStreamingCall(request);
        } catch (UnsupportedOperationException e) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }
    }
}
