package com.leyou.item.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void method1() {
        redisTemplate.opsForValue().set("name", "jack");
        String name = redisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);
    }

    @Test
    public void method2() {
        redisTemplate.opsForValue().set("age", "18", 10000);
    }

    @Test
    public void method3() {
        BoundHashOperations<String, Object, Object> user = redisTemplate.boundHashOps("user");
        user.put("name", "lisi");
        user.put("age","18");
        Object name = user.get("name");
        System.out.println("name = " + name);
        Map<Object, Object> entries = user.entries();
        System.out.println("entries = " + entries);
    }

    @Test
    public void method4() {
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.rightPushAll("users", "zhangsan", "lisi", "wangwu");
        List<String> users = list.range("users", 0, 2);
        System.out.println("users = " + users);

    }
}
