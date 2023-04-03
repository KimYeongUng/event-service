package com.example.eventservice.router;

import com.example.eventservice.handler.FluxHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
@Slf4j
public class RoutesConfig {
    // add bean
    @Bean
    public RouterFunction<ServerResponse> route(FluxHandler handler){
        return RouterFunctions.route(
                RequestPredicates.GET("/getmap").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                ,handler::myMono
        );
    }

    @Bean
    public RouterFunction<ServerResponse> eventRoute(FluxHandler handler){
        return RouterFunctions.route(
                RequestPredicates.GET("/getevent").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                ,handler::getEvent
        );
    }

    @Bean
    public RouterFunction<ServerResponse> returnParam(FluxHandler handler){
        return RouterFunctions.route(
                RequestPredicates.GET("/reparam").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                ,handler::getParam
        );
    }

    @Bean
    public RouterFunction<ServerResponse> saveProduct(FluxHandler handler){
        return RouterFunctions.route(
          RequestPredicates.GET("/save-product").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                ,handler::saveProduct
        );
    }
}
