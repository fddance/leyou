package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.IAuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.item.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private JwtProperties prop;

    @PostMapping("login")
    public ResponseEntity<Void> login(
            @RequestParam("username") String username, @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response
    ) {
        String token = authService.login(username, password);
        CookieUtils.newBuilder(response).httpOnly().request(request).build(prop.getCookieName(), token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("verify")
    public ResponseEntity<UserInfo> getUserInfo(@CookieValue("LY_TOKEN") String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            CookieUtils.newBuilder(response).httpOnly().request(request).build(prop.getCookieName(), token);
            return ResponseEntity.ok(infoFromToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
