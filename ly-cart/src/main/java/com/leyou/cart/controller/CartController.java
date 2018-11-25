package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 将商品添加进购物车
     *
     * @param cart
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 查询用户购物车
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Cart>> getCartList() {
        return ResponseEntity.ok(cartService.getCartList());
    }

    /**
     * 修改购物车内商品数量
     *
     * @param id
     * @param num
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCart(@RequestParam("id") Long id, @RequestParam("num") Integer num) {
        cartService.updateCart(id, num);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除购物车内商品
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 将本地购物车添加进用户购物车
     * @param cartList
     * @return
     */
    @PostMapping("addLocalCarts")
    public ResponseEntity<Void> addLocalCarts(@RequestBody List<Cart> cartList) {
        cartService.addLocalCarts(cartList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
