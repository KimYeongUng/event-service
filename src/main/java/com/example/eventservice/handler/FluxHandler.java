package com.example.eventservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Optional;

@Component
@Slf4j
public class FluxHandler {
    public Mono<ServerResponse> myMono(ServerRequest request){

        HashMap<Object,Object> res = new HashMap<>();
        Mono<HashMap<Object,Object>> map = Mono.just(res);

        // set result data json
        res.put("key1",1);
        res.put("key2",2);

        map.subscribe(item->{
            log.info("item: {}",item);
        });

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(map,HashMap.class));
    }

    public Mono<ServerResponse> getEvent(ServerRequest request){

        HashMap<Object,Object> event = new HashMap<>();
        Mono<HashMap<Object,Object>> eventmap = Mono.just(event);

        log.info("getEvent");
        event.put("event",1);

        eventmap.subscribe(item->{
            log.info("item:{}",item);
        });

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(eventmap,HashMap.class));
    }

    public Mono<ServerResponse> getParam(ServerRequest request){
        Optional<String> s = request.queryParam("param1");
        String text =  s.get();
        Mono<String> map = Mono.just(text);
        map.subscribe(item->log.info("param: {}",item));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(map,String.class));
    }

    public Mono<ServerResponse> saveProduct(ServerRequest request){
        MultiValueMap<String, String> s = request.queryParams();
        s.forEach((k,v)->log.info("param:{} {}",k,v));
        String ok = "ok";
        Mono<String> map = Mono.just(ok);
        map.subscribe();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(map,String.class));
    }

}
