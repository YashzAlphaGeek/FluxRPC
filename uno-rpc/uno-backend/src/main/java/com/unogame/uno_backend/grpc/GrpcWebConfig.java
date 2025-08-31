package com.unogame.uno_backend.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;


@Configuration
public class GrpcWebConfig {

    @Bean
    @GrpcGlobalServerInterceptor
    public ServerInterceptor grpcWebInterceptor() {
        return new GrpcWebInterceptor();
    }
}


