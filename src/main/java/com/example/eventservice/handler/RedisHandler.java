package com.example.eventservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RedisHandler {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<Object,Object> operations;
    private final ReactiveRedisTemplate<Object,Object> template;
    private static final AtomicInteger count = new AtomicInteger(0);


    RedisHandler(ReactiveRedisConnectionFactory factory,ReactiveRedisOperations<Object,Object> operations,
                 ReactiveRedisTemplate<Object,Object> template){
        this.factory = factory;
        this.operations = operations;
        this.template = template;
    }

    Flux<Object> getList(){
        return operations.keys("*")
                .flatMap(key->operations.opsForValue().get(key));
    }
}
