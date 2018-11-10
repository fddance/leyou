package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> queryCategoryList(Long pid);

    List<Category> queryCategoryByBid(Long bid);
}
