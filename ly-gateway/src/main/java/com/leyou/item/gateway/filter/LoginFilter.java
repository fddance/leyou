package com.leyou.item.gateway.filter;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.item.common.utils.CookieUtils;
import com.leyou.item.gateway.config.AllowPathProperties;
import com.leyou.item.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Component
@EnableConfigurationProperties({JwtProperties.class, AllowPathProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private AllowPathProperties allowPathProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        boolean flag = false;
        List<String> allowPaths = allowPathProperties.getAllowPaths();
        for (String allowPath : allowPaths) {
            if (requestURI.startsWith(allowPath)) {
                flag = true;
            }
        }
        return !flag;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = ctx.getRequest();
        // 获取token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        // 校验
        try {
            // 校验通过什么都不做，即放行
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
        } catch (Exception e) {
            // 校验出现异常，返回403
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(403);
        }
        return null;
    }
}