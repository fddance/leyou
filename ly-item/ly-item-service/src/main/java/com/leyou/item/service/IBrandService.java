package com.leyou.item.service;

import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.Brand;

public interface IBrandService {
    PageResult<Brand> queryBrandByPage(PageBean pageBean);
}
