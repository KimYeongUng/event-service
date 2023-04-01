package com.example.eventservice.router;

import com.example.eventservice.handler.FluxHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RoutesConfig {
    // add bean
    @Bean
    public RouterFunction<ServerResponse> route(FluxHandler handler){
        return RouterFunctions.route(
                RequestPredicates.GET("/getmap").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                ,handler::myMono
        );
    }
}
