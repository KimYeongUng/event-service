package com.example.eventservice.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;


public class DataTest {


    @Value("${spring.data.redis.port}")
    int port;

    @Value("${spring.data.redis.host}")
    String host;


}
