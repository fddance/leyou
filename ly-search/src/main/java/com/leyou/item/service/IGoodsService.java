package com.leyou.item.service;

import com.leyou.item.common.vo.PageResult;
import com.leyou.item.page.SearchRequest;
import com.leyou.item.pojo.Goods;

import java.util.List;

public interface IGoodsService {
    PageResult<Goods> getGoodsListByPage(SearchRequest searchRequest);
}
