package com.leyou.item.service.impl;

import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryList(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> select = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(select)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return select;
    }

    @Override
    public List<Category> queryCategoryByBid(Long bid) {
        List<Category> categoryList = categoryMapper.queryBrandCategoryIds(bid);
        if (categoryList.isEmpty()) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return categoryList;
    }

    @Override
    public List<Category> queryCategoryListByIds(List<Long> ids) {
        List<Category> categoryList = categoryMapper.selectByIdList(ids);
        if (categoryList.isEmpty()) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return categoryList;
    }
}
