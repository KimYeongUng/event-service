package com.example.eventservice.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void findStringKey(){
        final String key = "testkey";
        final ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,"value");
        final String res1 = valueOperations.get(key);
        assertEquals(res1,"value");

    }
}
