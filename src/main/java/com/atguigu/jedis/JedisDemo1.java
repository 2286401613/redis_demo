package com.atguigu.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisDemo1 {
    public static void main(String[] args) {
        //创建jedis对象
        Jedis jedis=new Jedis("127.0.0.1",6379);
        //测试redis
        String value = jedis.ping();
        System.out.println(value);
        jedis.sadd("name","lucy","jack");
        Set<String> names = jedis.smembers("name");
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void test(){
        //创建jedis对象
        Jedis jedis=new Jedis("127.0.0.1",6379);
        //测试redis
        String value = jedis.ping();
        System.out.println(value);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("key = " + key);
        }
    }
}