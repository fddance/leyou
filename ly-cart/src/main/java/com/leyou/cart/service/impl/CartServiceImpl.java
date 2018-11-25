package com.leyou.cart.service.impl;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.filter.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.ICartService;
import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PRE = "ly:cart:user";

    @Override
    public void addCart(Cart cart) {
        UserInfo user = LoginInterceptor.getUser();
        String key = KEY_PRE + user.getId();
        String hkey = cart.getSkuId().toString();
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
        if (hashOps.hasKey(hkey)) {
            int num = cart.getNum();
            String json = hashOps.get(hkey);
            cart = JsonUtils.toBean(json, Cart.class);
            cart.setNum(cart.getNum() + num);
        }
        hashOps.put(hkey, JsonUtils.toString(cart));
    }

    @Override
    public List<Cart> getCartList() {
        UserInfo user = LoginInterceptor.getUser();
        String key = KEY_PRE + user.getId();
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
        List<String> values = hashOps.values();
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        List<Cart> collect = values.stream().map(cart -> JsonUtils.toBean(cart, Cart.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void updateCart(Long id, Integer num) {
        UserInfo user = LoginInterceptor.getUser();
        String key = KEY_PRE + user.getId();
        if (!redisTemplate.hasKey(key)) {
            throw new LyException(ExceptionEnum.CART_SERVER_ERROR);
        }
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
        String s = hashOps.get(id.toString());
        Cart cart = JsonUtils.toBean(s, Cart.class);
        cart.setNum(num);
        hashOps.put(id.toString(), JsonUtils.toString(cart));
    }

    @Override
    public void deleteCart(Long id) {
        UserInfo user = LoginInterceptor.getUser();
        String key = KEY_PRE + user.getId();
        if (!redisTemplate.hasKey(key)) {
            throw new LyException(ExceptionEnum.CART_SERVER_ERROR);
        }
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
        Long delete = hashOps.delete(id.toString());
        if (delete != 1) {
            throw new LyException(ExceptionEnum.CART_SERVER_ERROR);
        }
    }

    @Override
    public void addLocalCarts(List<Cart> cartList) {
        UserInfo user = LoginInterceptor.getUser();
        String key = KEY_PRE + user.getId();
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(key);
        if (CollectionUtils.isEmpty(cartList)) {
            return;
        }
        for (Cart cart : cartList) {
            hashOps.put(cart.getSkuId().toString(), JsonUtils.toString(cart));
        }

    }
}
