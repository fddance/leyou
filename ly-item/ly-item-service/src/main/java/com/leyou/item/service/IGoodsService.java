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
     * @return
     */
    PageResult<Spu> querySpu(Integer page, Integer rows, Boolean saleable, String key);

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

    /**
     * 修改商品上下架信息
     * @param id
     * @param saleable
     */
    void editSaleable(Long id, Boolean saleable);

    /**
     * 通过id删除商品信息
     * @param id
     */
    void deleteGoodsById(Long id);

    /**
     * 通过id查询spu
     * @param id
     * @return
     */
    Spu selectSpuById(Long id);

    List<Sku> selectSkusByIds(List<Long> ids);
}
