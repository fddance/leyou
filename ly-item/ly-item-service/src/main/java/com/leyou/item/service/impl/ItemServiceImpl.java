package com.leyou.item.service.impl;

import com.leyou.item.pojo.Item;
import com.leyou.item.service.IItemService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ItemServiceImpl implements IItemService {
    @Override
    public Item addItem(Item item) {
        item.setId(new Random().nextInt(100));
        return item;
    }
}
