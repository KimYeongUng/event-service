package com.eventservice.handler;

import com.eventservice.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class RedisHandler {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    Mono<Map<String,String>> resmap;

    public Mono<ServerResponse> set(ServerRequest request){
        Map<String,String> req = request.queryParams().toSingleValueMap();
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

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

    public Mono<ServerResponse> getUserInfo(ServerRequest request){
        Map<String,String> req = request.queryParams().toSingleValueMap();
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        Map<String,String> map = new HashMap<>();
        req.forEach((k,v)-> {
                    log.info((String) valueOperations.get(v));
                    map.put(v, (String) valueOperations.get(v));
                }
        );
        resmap = Mono.just(map);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(resmap,Map.class));
    }

    public Mono<ServerResponse> setUserInfo(ServerRequest request){

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        UserVO vo = new UserVO();
        vo.setId(request.queryParam("userid").get());
        vo.setName(request.queryParam("username").get());
        vo.setPasswd(request.queryParam("passwd").get());

        valueOperations.set(request.queryParam("userid").get(),vo);

        Mono<String> res = Mono.just(request.queryParam("userid").get());
        res.subscribe();
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromProducer(res,String.class));
    }

    public Mono<ServerResponse> get(ServerRequest request){
        String userid = request.queryParam("userid").get();
        log.info("usrID: "+userid);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
        redisTemplate.opsForValue();

        Mono<String> res = Mono.just(userid);
        res.subscribe();
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromProducer(res,String.class));
    }
}
