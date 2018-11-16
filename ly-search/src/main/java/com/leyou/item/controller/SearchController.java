package com.leyou.item.controller;

import com.leyou.item.common.vo.PageResult;
import com.leyou.item.page.SearchRequest;
import com.leyou.item.pojo.Goods;
import com.leyou.item.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("page")
    public ResponseEntity<PageResult<Goods>> getGoodsListByPage(SearchRequest searchRequest) {
        return ResponseEntity.ok(goodsService.getGoodsListByPage(searchRequest));
    }
}
