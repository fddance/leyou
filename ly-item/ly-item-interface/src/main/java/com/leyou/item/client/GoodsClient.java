package com.leyou.item.client;

import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface GoodsClient {

    /**
     * 分页查询商品
     * @return
     */
    @RequestMapping("spu/page")
    PageResult<Spu> querySpu(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    );

    /**
     * 通过spu的id查询sku集合
     * @param id
     * @return
     */
    @GetMapping("sku/list")
     List<Sku> selectSkusBySpuId(@RequestParam("id") Long id);

    /**
     * 通过spu的id查询spu的详细信息
     * @param id
     * @return
     */
    @GetMapping("spu/detail/{id}")
    SpuDetail selectGoodsDetail(@PathVariable("id") Long id);




}
