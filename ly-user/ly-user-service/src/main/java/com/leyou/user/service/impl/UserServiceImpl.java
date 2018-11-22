package com.leyou.user.service.impl;

import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.IUserService;
import com.leyou.user.utils.CodecUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
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
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
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

    @Override
    public void addUser(User user, String code) {
        user.setId(null);
        user.setCreated(new Date());
        String key = KEY_PREFIX + user.getPhone();
        String code_1 = redisTemplate.opsForValue().get(key);
        if (!code.equals(code_1)) {
            throw new LyException(ExceptionEnum.INVALID_PARAM_TYPE);
        }
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), user.getSalt()));
        int count = userMapper.insert(user);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_USER_SERVER_ERROR);
        }
        redisTemplate.delete(key);
    }

    @Override
    public User queryUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user = userMapper.selectOne(user);
        if (user == null) {
            throw new LyException(ExceptionEnum.CUSTOM_ERROR.write(400, "用户名或密码错误"));
        }
        if(!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))){
            throw new LyException(ExceptionEnum.CUSTOM_ERROR.write(400, "用户名或密码错误"));
        }
        return user;
    }
}
