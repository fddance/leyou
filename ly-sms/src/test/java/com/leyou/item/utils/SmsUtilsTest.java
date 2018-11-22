package com.leyou.item.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsUtilsTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void method1() {
        Map<String,String> map = new HashMap<>();
        map.put("phone", "15537795171");
        map.put("code", "123321");
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", map);
    }

}