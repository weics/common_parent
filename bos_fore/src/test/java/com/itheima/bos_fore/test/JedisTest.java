package com.itheima.bos_fore.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JedisTest {
    @Autowired
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @Test
    public void test1() {
        //创建一个连接,连接本地redis服务
        Jedis jedis = new Jedis("192.168.99.20", 6379);
        jedis.set("key1", "value1");
        System.out.println(jedis.get("key1"));
        jedis.close();
    }

    @Test
    public void test2() {
        Object key1 = stringObjectRedisTemplate.opsForValue().get("key1");
        System.out.println(key1);
        stringObjectRedisTemplate.delete("key1");
        stringObjectRedisTemplate.opsForValue().set("k1", "v1", 10, TimeUnit.SECONDS);
    }
}
