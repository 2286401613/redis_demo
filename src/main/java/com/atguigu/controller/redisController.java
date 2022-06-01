package com.atguigu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class redisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/setInfo")
    public String testredisTemplate(){
        redisTemplate.opsForValue().set("info","123");
        return (String) redisTemplate.opsForValue().get("info");
    }
}