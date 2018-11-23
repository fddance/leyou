package com.leyou.item.controller;

import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> querySpu(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    ) {
        return ResponseEntity.ok(goodsService.querySpu(page, rows, saleable, key));
    }

    @PostMapping("goods")
    public ResponseEntity<Void> addGoods(@RequestBody Spu spu) {
        goodsService.addGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SpuDetail> selectGoodsDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(goodsService.selectGoodsDetail(id));
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> selectSkusBySpuId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(goodsService.selectSkusBySpuId(id));
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu) {
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/spu/editSaleable/{id}/{saleable}")
    public ResponseEntity<Void> editSaleable(@PathVariable("id") Long id,
                                             @PathVariable("saleable") Boolean saleable) {
        goodsService.editSaleable(id, saleable);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("spu/delete/{id}")
    public ResponseEntity<Void> deleteGoodsById(@PathVariable("id") Long id) {
        goodsService.deleteGoodsById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/spu")
    public ResponseEntity<Spu> selectSpuById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(goodsService.selectSpuById(id));
    }

    /**
     * 通过sku的id集合查询sku集合
     * @param ids
     * @return
     */
    @GetMapping("sku/list/ids")
    public ResponseEntity<List<Sku>> selectByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(goodsService.selectSkusByIds(ids));
    }
}
