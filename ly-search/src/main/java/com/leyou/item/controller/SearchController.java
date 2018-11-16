package com.leyou.item.controller;

import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.SearchRequest;
import com.leyou.item.pojo.Goods;
import com.leyou.item.pojo.SearchResult;
import com.leyou.item.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private IGoodsService goodsService;

    @PostMapping("page")
    public ResponseEntity<SearchResult<Goods>> getGoodsListByPage(@RequestBody SearchRequest searchRequest) {
        SearchResult<Goods> listByPage = goodsService.getGoodsListByPage(searchRequest);
        System.out.println("listByPage = " + listByPage);
        return ResponseEntity.ok(listByPage);
    }
}
