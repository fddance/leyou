package com.leyou.item.service.impl;

import com.leyou.item.common.vo.PageResult;
import com.leyou.item.page.SearchRequest;
import com.leyou.item.pojo.Goods;
import com.leyou.item.repository.GoodsRepository;
import com.leyou.item.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodesServiceImpl implements IGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public PageResult<Goods> getGoodsListByPage(SearchRequest searchRequest) {

        String key = searchRequest.getKey();

        if (StringUtils.isBlank(key)) {
            return null;
        }

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));
        queryBuilder.withPageable(PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize()));
        Page<Goods> search = goodsRepository.search(queryBuilder.build());
        return new PageResult<>(search.getTotalElements(),search.getTotalPages(),search.getContent());
    }
}
