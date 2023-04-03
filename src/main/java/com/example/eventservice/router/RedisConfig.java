package com.example.eventservice.router;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    ReactiveRedisTemplate<String, String> redisOperations(ReactiveRedisConnectionFactory factory){
        RedisSerializer<String> serializer = new StringRedisSerializer();
        RedisSerializationContext<String,String> context = RedisSerializationContext
                .<String,String>newSerializationContext().key(serializer).value(serializer).hashKey(serializer)
                .hashValue(serializer).build();
        return new ReactiveRedisTemplate<>(factory,context);
    }
}
