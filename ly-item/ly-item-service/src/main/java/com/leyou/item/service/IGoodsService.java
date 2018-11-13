package com.leyou.item.service;

import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

public interface IGoodsService {
    /**
     * 根据前端信息查询商品列表
     * @param pageBean
     * @return
     */
    PageResult<Spu> querySpu(PageBean pageBean);

    /**
     * 添加商品详情
     * @param spu
     */
    void addGoods(Spu spu);

    /**
     * 根据商品spu的id查询商品信息
     * @param id
     * @return
     */
    SpuDetail selectGoodsDetail(Long id);

    /**
     * 根据商品spu的id查询商品的sku集合
     * @param id
     * @return
     */
    List<Sku> selectSkusBySpuId(Long id);

    /**
     * 根据页面信息修改商品spu信息
     * @param spu
     */
    void updateGoods(Spu spu);
}