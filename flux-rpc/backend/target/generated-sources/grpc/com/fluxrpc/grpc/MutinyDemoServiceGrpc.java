package com.fluxrpc.grpc;

import static com.fluxrpc.grpc.DemoServiceGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@jakarta.annotation.Generated(value = "by Mutiny Grpc generator", comments = "Source: streaming_demo.proto")
public final class MutinyDemoServiceGrpc implements io.quarkus.grpc.MutinyGrpc {

    private MutinyDemoServiceGrpc() {
    }

    public static MutinyDemoServiceStub newMutinyStub(io.grpc.Channel channel) {
        return new MutinyDemoServiceStub(channel);
    }

    public static class MutinyDemoServiceStub extends io.grpc.stub.AbstractStub<MutinyDemoServiceStub> implements io.quarkus.grpc.MutinyStub {

        private DemoServiceGrpc.DemoServiceStub delegateStub;

        private MutinyDemoServiceStub(io.grpc.Channel channel) {
            super(channel);
            delegateStub = DemoServiceGrpc.newStub(channel);
        }

        private MutinyDemoServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
            delegateStub = DemoServiceGrpc.newStub(channel).build(channel, callOptions);
        }

        @Override
        protected MutinyDemoServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new MutinyDemoServiceStub(channel, callOptions);
        }

        public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.UnaryResponse> unaryCall(com.fluxrpc.grpc.UnaryRequest request) {
            return io.quarkus.grpc.stubs.ClientCalls.oneToOne(request, delegateStub::unaryCall);
        }

        public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ServerStreamingResponse> serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request) {
            return io.quarkus.grpc.stubs.ClientCalls.oneToMany(request, delegateStub::serverStreamingCall);
        }

        public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.ClientStreamingResponse> clientStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ClientStreamingRequest> request) {
            return io.quarkus.grpc.stubs.ClientCalls.manyToOne(request, delegateStub::clientStreamingCall);
        }

        public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiResponse> biDiStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiRequest> request) {
            return io.quarkus.grpc.stubs.ClientCalls.manyToMany(request, delegateStub::biDiStreamingCall);
        }
    }

    public static abstract class DemoServiceImplBase implements io.grpc.BindableService {

        private String compression;

        /**
         * Set whether the server will try to use a compressed response.
         *
         * @param compression the compression, e.g {@code gzip}
         */
        public DemoServiceImplBase withCompression(String compression) {
            this.compression = compression;
            return this;
        }

        public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.UnaryResponse> unaryCall(com.fluxrpc.grpc.UnaryRequest request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ServerStreamingResponse> serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        public io.smallrye.mutiny.Uni<com.fluxrpc.grpc.ClientStreamingResponse> clientStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.ClientStreamingRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        public io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiResponse> biDiStreamingCall(io.smallrye.mutiny.Multi<com.fluxrpc.grpc.BiDiRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override
        public io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(com.fluxrpc.grpc.DemoServiceGrpc.getUnaryCallMethod(), asyncUnaryCall(new MethodHandlers<com.fluxrpc.grpc.UnaryRequest, com.fluxrpc.grpc.UnaryResponse>(this, METHODID_UNARY_CALL, compression))).addMethod(com.fluxrpc.grpc.DemoServiceGrpc.getServerStreamingCallMethod(), asyncServerStreamingCall(new MethodHandlers<com.fluxrpc.grpc.ServerStreamingRequest, com.fluxrpc.grpc.ServerStreamingResponse>(this, METHODID_SERVER_STREAMING_CALL, compression))).addMethod(com.fluxrpc.grpc.DemoServiceGrpc.getClientStreamingCallMethod(), asyncClientStreamingCall(new MethodHandlers<com.fluxrpc.grpc.ClientStreamingRequest, com.fluxrpc.grpc.ClientStreamingResponse>(this, METHODID_CLIENT_STREAMING_CALL, compression))).addMethod(com.fluxrpc.grpc.DemoServiceGrpc.getBiDiStreamingCallMethod(), asyncBidiStreamingCall(new MethodHandlers<com.fluxrpc.grpc.BiDiRequest, com.fluxrpc.grpc.BiDiResponse>(this, METHODID_BI_DI_STREAMING_CALL, compression))).build();
        }
    }

    private static final int METHODID_UNARY_CALL = 0;

    private static final int METHODID_SERVER_STREAMING_CALL = 1;

    private static final int METHODID_CLIENT_STREAMING_CALL = 2;

    private static final int METHODID_BI_DI_STREAMING_CALL = 3;

    private static final class MethodHandlers<Req, Resp> implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>, io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {

        private final DemoServiceImplBase serviceImpl;

        private final int methodId;

        private final String compression;

        MethodHandlers(DemoServiceImplBase serviceImpl, int methodId, String compression) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
            this.compression = compression;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                case METHODID_UNARY_CALL:
                    io.quarkus.grpc.stubs.ServerCalls.oneToOne((com.fluxrpc.grpc.UnaryRequest) request, (io.grpc.stub.StreamObserver<com.fluxrpc.grpc.UnaryResponse>) responseObserver, compression, serviceImpl::unaryCall);
                    break;
                case METHODID_SERVER_STREAMING_CALL:
                    io.quarkus.grpc.stubs.ServerCalls.oneToMany((com.fluxrpc.grpc.ServerStreamingRequest) request, (io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ServerStreamingResponse>) responseObserver, compression, serviceImpl::serverStreamingCall);
                    break;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                case METHODID_CLIENT_STREAMING_CALL:
                    return (io.grpc.stub.StreamObserver<Req>) io.quarkus.grpc.stubs.ServerCalls.manyToOne((io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ClientStreamingResponse>) responseObserver, serviceImpl::clientStreamingCall);
                case METHODID_BI_DI_STREAMING_CALL:
                    return (io.grpc.stub.StreamObserver<Req>) io.quarkus.grpc.stubs.ServerCalls.manyToMany((io.grpc.stub.StreamObserver<com.fluxrpc.grpc.BiDiResponse>) responseObserver, serviceImpl::biDiStreamingCall);
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }
}
