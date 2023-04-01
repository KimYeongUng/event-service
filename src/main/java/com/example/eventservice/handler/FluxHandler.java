package com.example.eventservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
@Slf4j
public class FluxHandler {
    private HashMap<Object,Object> res = new HashMap<>();
    private Mono<HashMap<Object,Object>> map = Mono.just(res);

    public Mono<ServerResponse> myMono(ServerRequest request){
        // set result data json
        res.put("key1",1);
        res.put("key2",2);

        map.subscribe(item->{
            log.info("item: {}",item);
        });

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(map,HashMap.class));
    }
}
