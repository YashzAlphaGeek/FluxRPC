package com.unogame.uno_backend.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

public class GrpcWebInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        // Handle preflight OPTIONS for browsers
        if ("OPTIONS".equalsIgnoreCase(call.getMethodDescriptor().getFullMethodName())) {
            call.close(Status.OK, new Metadata());
            return new ServerCall.Listener<>() {};
        }

        return next.startCall(call, headers);
    }
}
