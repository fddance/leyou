package com.leyou.item.controller;


import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.pojo.Item;
import com.leyou.item.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private IItemService itemService;

    @RequestMapping("addItem")
    public ResponseEntity<Item> addItem(Item item) {
        if (item.getPrice() == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            throw new LyException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(item));
    }

    @RequestMapping("haha")
    public ResponseEntity<String> haha() {
        return ResponseEntity.status(HttpStatus.OK).body("haha");
    }
}
