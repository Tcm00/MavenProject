package com.example.demo.controller;

import com.example.demo.annotation.Antishake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

/**
 * @author tangcanming
 * @date 2024/1/11 14:53
 * @describe
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/get")
    @Antishake()
    public Object getUser() {
        log.debug("=========================");
        return redisTemplate.opsForValue().get("user");
    }
    @GetMapping("/set")
    @Antishake(9900)
    public String setUser(String key ,String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.opsForValue().set("myuser", "myuser");
        return "success";
    }
}
