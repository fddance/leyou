package com.leyou.item.service;

import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

public interface IBrandService {
    /**
     * 通过页面传过来信息查询页面数据
     * @param pageBean
     * @return
     */
    PageResult<Brand> queryBrandByPage(PageBean pageBean);

    /**
     * 新增品牌
     * @param brand 品牌信息
     * @param cidList   品牌所属类型
     */
    void addBrand(Brand brand, List<Long> cidList);
}
