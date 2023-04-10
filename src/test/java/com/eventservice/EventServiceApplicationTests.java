package com.eventservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EventServiceApplicationTests {

    @Value("${spring.data.redis.host}")
    String host;

    @Value("${spring.data.redis.host}")
    int port;
    @Test
    void contextLoads() {
        assertEquals(host,"localhost");
        assertEquals(port,6379);
    }

}
