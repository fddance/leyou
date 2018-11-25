package com.leyou.cart.service;

import com.leyou.cart.pojo.Cart;

import java.util.List;

public interface ICartService {
    void addCart(Cart cart);

    List<Cart> getCartList();

    void updateCart(Long id, Integer num);

    void deleteCart(Long id);

    void addLocalCarts(List<Cart> cartList);
}
