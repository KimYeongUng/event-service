package com.eventservice.handler;

import com.eventservice.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Slf4j
@Component
public class RedisHandler {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public Mono<ServerResponse> setUserInfo(ServerRequest request){

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        UserVO vo = new UserVO();

        // Optional isPresent 분기처리 필요
        vo.setId(request.queryParam("userid").get());
        vo.setName(request.queryParam("username").get());
        vo.setPasswd(request.queryParam("passwd").get());

        valueOperations.set(request.queryParam("userid").get(),vo);

        Mono<String> res = Mono.just(request.queryParam("userid").get());
        res.subscribe();
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromProducer(res,String.class));
    }

    public Mono<ServerResponse> getUserInfo(ServerRequest request){
        String userid = request.queryParam("userid").get();
        log.info("usrID: "+userid);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(UserVO.class));
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        UserVO vo = (UserVO) valueOperations.get(userid);

        Mono<UserVO> res = Mono.just(Objects.requireNonNull(vo));
        res.subscribe();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(res,UserVO.class));
    }
}
