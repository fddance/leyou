package com.leyou.auth.service.impl;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.IAuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.user.client.UserClient;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties prop;

    @Override
    public String login(String username, String password) {
        try {
            User user = userClient.queryUser(username, password);
            if (user == null) {
                throw new LyException(ExceptionEnum.INVALID_USERNAME_OR_PASSWORD);
            }
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername());
            String token = JwtUtils.generateToken(userInfo, prop.getPrivateKey(), 30);
            return token;
        } catch (Exception e) {
            log.error("用户{}登录时发生异常",username,e);
            throw new LyException(ExceptionEnum.INVALID_USERNAME_OR_PASSWORD);
        }
    }
}
