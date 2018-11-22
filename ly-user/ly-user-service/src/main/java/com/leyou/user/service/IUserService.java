package com.leyou.user.service;

public interface IUserService {
    Boolean checkData(String data, Integer type);

    void sendCheckCode(String phone);
}
