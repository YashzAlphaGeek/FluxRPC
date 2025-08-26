package com.fluxrpc.grpc;

import io.grpc.*;

public class GrpcInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
        ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        System.out.println("Incoming call: " + call.getMethodDescriptor().getFullMethodName());
        try {
            return next.startCall(call, headers);
        } catch (Exception e) {
            call.close(Status.INTERNAL.withDescription(e.getMessage()), headers);
            throw e;
        }
    }
}

