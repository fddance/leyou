package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 验证数据是否合法
     *
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("send")
    public ResponseEntity<Void> sendCheckCode(@RequestParam(value = "phone") String phone) {
        userService.sendCheckCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 用户注册
     * @param code   验证码
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> addUser(User user,@RequestParam("code")String code
    ) {
        userService.addUser(user,code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
