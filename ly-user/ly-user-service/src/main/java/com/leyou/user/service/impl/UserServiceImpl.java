package com.leyou.user.service.impl;

import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.IUserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:phone:";

    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setPhone(data);
                break;
            case 2:
                user.setUsername(data);
                break;
            default:
                throw new LyException(ExceptionEnum.INVALID_PARAM_TYPE);
        }
        int count = userMapper.selectCount(user);
        return count == 0;
    }

    @Override
    public void sendCheckCode(String phone) {
        if (!phone.matches("^1[35678]\\d{9}$")) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_TYPE);
        }

        String key = KEY_PREFIX + phone;

        String code = NumberUtils.generateCode(6);
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", map);

        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }
}
