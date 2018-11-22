package com.leyou.user.service;

import com.leyou.user.pojo.User;

public interface IUserService {
    Boolean checkData(String data, Integer type);

    void sendCheckCode(String phone);

    void addUser(User user, String code);
}
