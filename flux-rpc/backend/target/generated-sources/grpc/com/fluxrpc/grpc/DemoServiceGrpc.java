package com.fluxrpc.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.quarkus.Generated(value = "by gRPC proto compiler (version 1.69.1)", comments = "Source: streaming_demo.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DemoServiceGrpc {

    private DemoServiceGrpc() {
    }

    public static final java.lang.String SERVICE_NAME = "DemoService";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<com.fluxrpc.grpc.UnaryRequest, com.fluxrpc.grpc.UnaryResponse> getUnaryCallMethod;

    @io.grpc.stub.annotations.RpcMethod(fullMethodName = SERVICE_NAME + '/' + "UnaryCall", requestType = com.fluxrpc.grpc.UnaryRequest.class, responseType = com.fluxrpc.grpc.UnaryResponse.class, methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<com.fluxrpc.grpc.UnaryRequest, com.fluxrpc.grpc.UnaryResponse> getUnaryCallMethod() {
        io.grpc.MethodDescriptor<com.fluxrpc.grpc.UnaryRequest, com.fluxrpc.grpc.UnaryResponse> getUnaryCallMethod;
        if ((getUnaryCallMethod = DemoServiceGrpc.getUnaryCallMethod) == null) {
            synchronized (DemoServiceGrpc.class) {
                if ((getUnaryCallMethod = DemoServiceGrpc.getUnaryCallMethod) == null) {
                    DemoServiceGrpc.getUnaryCallMethod = getUnaryCallMethod = io.grpc.MethodDescriptor.<com.fluxrpc.grpc.UnaryRequest, com.fluxrpc.grpc.UnaryResponse>newBuilder().setType(io.grpc.MethodDescriptor.MethodType.UNARY).setFullMethodName(generateFullMethodName(SERVICE_NAME, "UnaryCall")).setSampledToLocalTracing(true).setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.UnaryRequest.getDefaultInstance())).setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.UnaryResponse.getDefaultInstance())).setSchemaDescriptor(new DemoServiceMethodDescriptorSupplier("UnaryCall")).build();
                }
            }
        }
        return getUnaryCallMethod;
    }

    private static volatile io.grpc.MethodDescriptor<com.fluxrpc.grpc.ServerStreamingRequest, com.fluxrpc.grpc.ServerStreamingResponse> getServerStreamingCallMethod;

    @io.grpc.stub.annotations.RpcMethod(fullMethodName = SERVICE_NAME + '/' + "ServerStreamingCall", requestType = com.fluxrpc.grpc.ServerStreamingRequest.class, responseType = com.fluxrpc.grpc.ServerStreamingResponse.class, methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
    public static io.grpc.MethodDescriptor<com.fluxrpc.grpc.ServerStreamingRequest, com.fluxrpc.grpc.ServerStreamingResponse> getServerStreamingCallMethod() {
        io.grpc.MethodDescriptor<com.fluxrpc.grpc.ServerStreamingRequest, com.fluxrpc.grpc.ServerStreamingResponse> getServerStreamingCallMethod;
        if ((getServerStreamingCallMethod = DemoServiceGrpc.getServerStreamingCallMethod) == null) {
            synchronized (DemoServiceGrpc.class) {
                if ((getServerStreamingCallMethod = DemoServiceGrpc.getServerStreamingCallMethod) == null) {
                    DemoServiceGrpc.getServerStreamingCallMethod = getServerStreamingCallMethod = io.grpc.MethodDescriptor.<com.fluxrpc.grpc.ServerStreamingRequest, com.fluxrpc.grpc.ServerStreamingResponse>newBuilder().setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING).setFullMethodName(generateFullMethodName(SERVICE_NAME, "ServerStreamingCall")).setSampledToLocalTracing(true).setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.ServerStreamingRequest.getDefaultInstance())).setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.ServerStreamingResponse.getDefaultInstance())).setSchemaDescriptor(new DemoServiceMethodDescriptorSupplier("ServerStreamingCall")).build();
                }
            }
        }
        return getServerStreamingCallMethod;
    }

    private static volatile io.grpc.MethodDescriptor<com.fluxrpc.grpc.ClientStreamingRequest, com.fluxrpc.grpc.ClientStreamingResponse> getClientStreamingCallMethod;

    @io.grpc.stub.annotations.RpcMethod(fullMethodName = SERVICE_NAME + '/' + "ClientStreamingCall", requestType = com.fluxrpc.grpc.ClientStreamingRequest.class, responseType = com.fluxrpc.grpc.ClientStreamingResponse.class, methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
    public static io.grpc.MethodDescriptor<com.fluxrpc.grpc.ClientStreamingRequest, com.fluxrpc.grpc.ClientStreamingResponse> getClientStreamingCallMethod() {
        io.grpc.MethodDescriptor<com.fluxrpc.grpc.ClientStreamingRequest, com.fluxrpc.grpc.ClientStreamingResponse> getClientStreamingCallMethod;
        if ((getClientStreamingCallMethod = DemoServiceGrpc.getClientStreamingCallMethod) == null) {
            synchronized (DemoServiceGrpc.class) {
                if ((getClientStreamingCallMethod = DemoServiceGrpc.getClientStreamingCallMethod) == null) {
                    DemoServiceGrpc.getClientStreamingCallMethod = getClientStreamingCallMethod = io.grpc.MethodDescriptor.<com.fluxrpc.grpc.ClientStreamingRequest, com.fluxrpc.grpc.ClientStreamingResponse>newBuilder().setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING).setFullMethodName(generateFullMethodName(SERVICE_NAME, "ClientStreamingCall")).setSampledToLocalTracing(true).setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.ClientStreamingRequest.getDefaultInstance())).setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.ClientStreamingResponse.getDefaultInstance())).setSchemaDescriptor(new DemoServiceMethodDescriptorSupplier("ClientStreamingCall")).build();
                }
            }
        }
        return getClientStreamingCallMethod;
    }

    private static volatile io.grpc.MethodDescriptor<com.fluxrpc.grpc.BiDiRequest, com.fluxrpc.grpc.BiDiResponse> getBiDiStreamingCallMethod;

    @io.grpc.stub.annotations.RpcMethod(fullMethodName = SERVICE_NAME + '/' + "BiDiStreamingCall", requestType = com.fluxrpc.grpc.BiDiRequest.class, responseType = com.fluxrpc.grpc.BiDiResponse.class, methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
    public static io.grpc.MethodDescriptor<com.fluxrpc.grpc.BiDiRequest, com.fluxrpc.grpc.BiDiResponse> getBiDiStreamingCallMethod() {
        io.grpc.MethodDescriptor<com.fluxrpc.grpc.BiDiRequest, com.fluxrpc.grpc.BiDiResponse> getBiDiStreamingCallMethod;
        if ((getBiDiStreamingCallMethod = DemoServiceGrpc.getBiDiStreamingCallMethod) == null) {
            synchronized (DemoServiceGrpc.class) {
                if ((getBiDiStreamingCallMethod = DemoServiceGrpc.getBiDiStreamingCallMethod) == null) {
                    DemoServiceGrpc.getBiDiStreamingCallMethod = getBiDiStreamingCallMethod = io.grpc.MethodDescriptor.<com.fluxrpc.grpc.BiDiRequest, com.fluxrpc.grpc.BiDiResponse>newBuilder().setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING).setFullMethodName(generateFullMethodName(SERVICE_NAME, "BiDiStreamingCall")).setSampledToLocalTracing(true).setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.BiDiRequest.getDefaultInstance())).setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(com.fluxrpc.grpc.BiDiResponse.getDefaultInstance())).setSchemaDescriptor(new DemoServiceMethodDescriptorSupplier("BiDiStreamingCall")).build();
                }
            }
        }
        return getBiDiStreamingCallMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static DemoServiceStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<DemoServiceStub> factory = new io.grpc.stub.AbstractStub.StubFactory<DemoServiceStub>() {

            @java.lang.Override
            public DemoServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new DemoServiceStub(channel, callOptions);
            }
        };
        return DemoServiceStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static DemoServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<DemoServiceBlockingStub> factory = new io.grpc.stub.AbstractStub.StubFactory<DemoServiceBlockingStub>() {

            @java.lang.Override
            public DemoServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new DemoServiceBlockingStub(channel, callOptions);
            }
        };
        return DemoServiceBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static DemoServiceFutureStub newFutureStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<DemoServiceFutureStub> factory = new io.grpc.stub.AbstractStub.StubFactory<DemoServiceFutureStub>() {

            @java.lang.Override
            public DemoServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new DemoServiceFutureStub(channel, callOptions);
            }
        };
        return DemoServiceFutureStub.newStub(factory, channel);
    }

    /**
     */
    public interface AsyncService {

        /**
         */
        default void unaryCall(com.fluxrpc.grpc.UnaryRequest request, io.grpc.stub.StreamObserver<com.fluxrpc.grpc.UnaryResponse> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnaryCallMethod(), responseObserver);
        }

        /**
         */
        default void serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request, io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ServerStreamingResponse> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getServerStreamingCallMethod(), responseObserver);
        }

        /**
         */
        default io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ClientStreamingRequest> clientStreamingCall(io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ClientStreamingResponse> responseObserver) {
            return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getClientStreamingCallMethod(), responseObserver);
        }

        /**
         */
        default io.grpc.stub.StreamObserver<com.fluxrpc.grpc.BiDiRequest> biDiStreamingCall(io.grpc.stub.StreamObserver<com.fluxrpc.grpc.BiDiResponse> responseObserver) {
            return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getBiDiStreamingCallMethod(), responseObserver);
        }
    }

    /**
     * Base class for the server implementation of the service DemoService.
     */
    public static abstract class DemoServiceImplBase implements io.grpc.BindableService, AsyncService {

        @java.lang.Override
        public io.grpc.ServerServiceDefinition bindService() {
            return DemoServiceGrpc.bindService(this);
        }
    }

    /**
     * A stub to allow clients to do asynchronous rpc calls to service DemoService.
     */
    public static class DemoServiceStub extends io.grpc.stub.AbstractAsyncStub<DemoServiceStub> {

        private DemoServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected DemoServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DemoServiceStub(channel, callOptions);
        }

        /**
         */
        public void unaryCall(com.fluxrpc.grpc.UnaryRequest request, io.grpc.stub.StreamObserver<com.fluxrpc.grpc.UnaryResponse> responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(getChannel().newCall(getUnaryCallMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request, io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ServerStreamingResponse> responseObserver) {
            io.grpc.stub.ClientCalls.asyncServerStreamingCall(getChannel().newCall(getServerStreamingCallMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ClientStreamingRequest> clientStreamingCall(io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ClientStreamingResponse> responseObserver) {
            return io.grpc.stub.ClientCalls.asyncClientStreamingCall(getChannel().newCall(getClientStreamingCallMethod(), getCallOptions()), responseObserver);
        }

        /**
         */
        public io.grpc.stub.StreamObserver<com.fluxrpc.grpc.BiDiRequest> biDiStreamingCall(io.grpc.stub.StreamObserver<com.fluxrpc.grpc.BiDiResponse> responseObserver) {
            return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(getChannel().newCall(getBiDiStreamingCallMethod(), getCallOptions()), responseObserver);
        }
    }

    /**
     * A stub to allow clients to do synchronous rpc calls to service DemoService.
     */
    public static class DemoServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<DemoServiceBlockingStub> {

        private DemoServiceBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected DemoServiceBlockingStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DemoServiceBlockingStub(channel, callOptions);
        }

        /**
         */
        public com.fluxrpc.grpc.UnaryResponse unaryCall(com.fluxrpc.grpc.UnaryRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(getChannel(), getUnaryCallMethod(), getCallOptions(), request);
        }

        /**
         */
        public java.util.Iterator<com.fluxrpc.grpc.ServerStreamingResponse> serverStreamingCall(com.fluxrpc.grpc.ServerStreamingRequest request) {
            return io.grpc.stub.ClientCalls.blockingServerStreamingCall(getChannel(), getServerStreamingCallMethod(), getCallOptions(), request);
        }
    }

    /**
     * A stub to allow clients to do ListenableFuture-style rpc calls to service DemoService.
     */
    public static class DemoServiceFutureStub extends io.grpc.stub.AbstractFutureStub<DemoServiceFutureStub> {

        private DemoServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected DemoServiceFutureStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DemoServiceFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<com.fluxrpc.grpc.UnaryResponse> unaryCall(com.fluxrpc.grpc.UnaryRequest request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(getChannel().newCall(getUnaryCallMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_UNARY_CALL = 0;

    private static final int METHODID_SERVER_STREAMING_CALL = 1;

    private static final int METHODID_CLIENT_STREAMING_CALL = 2;

    private static final int METHODID_BI_DI_STREAMING_CALL = 3;

    private static final class MethodHandlers<Req, Resp> implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>, io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>, io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {

        private final AsyncService serviceImpl;

        private final int methodId;

        MethodHandlers(AsyncService serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                case METHODID_UNARY_CALL:
                    serviceImpl.unaryCall((com.fluxrpc.grpc.UnaryRequest) request, (io.grpc.stub.StreamObserver<com.fluxrpc.grpc.UnaryResponse>) responseObserver);
                    break;
                case METHODID_SERVER_STREAMING_CALL:
                    serviceImpl.serverStreamingCall((com.fluxrpc.grpc.ServerStreamingRequest) request, (io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ServerStreamingResponse>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch(methodId) {
                case METHODID_CLIENT_STREAMING_CALL:
                    return (io.grpc.stub.StreamObserver<Req>) serviceImpl.clientStreamingCall((io.grpc.stub.StreamObserver<com.fluxrpc.grpc.ClientStreamingResponse>) responseObserver);
                case METHODID_BI_DI_STREAMING_CALL:
                    return (io.grpc.stub.StreamObserver<Req>) serviceImpl.biDiStreamingCall((io.grpc.stub.StreamObserver<com.fluxrpc.grpc.BiDiResponse>) responseObserver);
                default:
                    throw new AssertionError();
            }
        }
    }

    public static io.grpc.ServerServiceDefinition bindService(AsyncService service) {
        return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(getUnaryCallMethod(), io.grpc.stub.ServerCalls.asyncUnaryCall(new MethodHandlers<com.fluxrpc.grpc.UnaryRequest, com.fluxrpc.grpc.UnaryResponse>(service, METHODID_UNARY_CALL))).addMethod(getServerStreamingCallMethod(), io.grpc.stub.ServerCalls.asyncServerStreamingCall(new MethodHandlers<com.fluxrpc.grpc.ServerStreamingRequest, com.fluxrpc.grpc.ServerStreamingResponse>(service, METHODID_SERVER_STREAMING_CALL))).addMethod(getClientStreamingCallMethod(), io.grpc.stub.ServerCalls.asyncClientStreamingCall(new MethodHandlers<com.fluxrpc.grpc.ClientStreamingRequest, com.fluxrpc.grpc.ClientStreamingResponse>(service, METHODID_CLIENT_STREAMING_CALL))).addMethod(getBiDiStreamingCallMethod(), io.grpc.stub.ServerCalls.asyncBidiStreamingCall(new MethodHandlers<com.fluxrpc.grpc.BiDiRequest, com.fluxrpc.grpc.BiDiResponse>(service, METHODID_BI_DI_STREAMING_CALL))).build();
    }

    private static abstract class DemoServiceBaseDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {

        DemoServiceBaseDescriptorSupplier() {
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return com.fluxrpc.grpc.StreamingDemoProto.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("DemoService");
        }
    }

    private static final class DemoServiceFileDescriptorSupplier extends DemoServiceBaseDescriptorSupplier {

        DemoServiceFileDescriptorSupplier() {
        }
    }

    private static final class DemoServiceMethodDescriptorSupplier extends DemoServiceBaseDescriptorSupplier implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {

        private final java.lang.String methodName;

        DemoServiceMethodDescriptorSupplier(java.lang.String methodName) {
            this.methodName = methodName;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (DemoServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME).setSchemaDescriptor(new DemoServiceFileDescriptorSupplier()).addMethod(getUnaryCallMethod()).addMethod(getServerStreamingCallMethod()).addMethod(getClientStreamingCallMethod()).addMethod(getBiDiStreamingCallMethod()).build();
                }
            }
        }
        return result;
    }
}
