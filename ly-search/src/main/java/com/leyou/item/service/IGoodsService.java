package com.leyou.item.service;

import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.SearchRequest;
import com.leyou.item.pojo.Goods;
import com.leyou.item.pojo.SearchResult;

public interface IGoodsService {
    SearchResult<Goods> getGoodsListByPage(SearchRequest searchRequest);
}
