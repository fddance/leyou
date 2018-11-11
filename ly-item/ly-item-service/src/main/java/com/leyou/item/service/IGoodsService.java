package com.leyou.item.service;

import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.Spu;

public interface IGoodsService {
    /**
     * 根据前端信息查询商品列表
     * @param pageBean
     * @return
     */
    PageResult<Spu> querySpu(PageBean pageBean);
}
