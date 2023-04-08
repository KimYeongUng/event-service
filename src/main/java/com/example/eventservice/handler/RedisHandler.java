package com.example.eventservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerResultHandler;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RedisHandler {

    @Autowired
    RedisTemplate redisTemplate;

    Mono<Map<String,String>> resmap;

    public Mono<ServerResponse> setData(ServerRequest request){
        Map<String,String> req = request.queryParams().toSingleValueMap();
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();

        req.forEach((k,v)->{
            valueOperations.set(k.trim(),v.trim());
        });

        Map<String,String> res = new HashMap<>();
        res.put("res","ok");
        resmap = Mono.just(res);
        resmap.subscribe();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(resmap,Map.class));
    }

    public Mono<ServerResponse> getData(ServerRequest request){
        Map<String,String> req = request.queryParams().toSingleValueMap();
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        Map<String,String> map = new HashMap<>();
        req.forEach((k,v)-> {
                    log.info(valueOperations.get(v));
                    map.put(v, valueOperations.get(v));
                }
        );
        resmap = Mono.just(map);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(resmap,Map.class));
    }
}
