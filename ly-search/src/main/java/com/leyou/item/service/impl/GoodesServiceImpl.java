package com.leyou.item.service.impl;

import com.leyou.item.client.BrandClient;
import com.leyou.item.client.CategoryClient;
import com.leyou.item.client.SpecClient;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.*;
import com.leyou.item.repository.GoodsRepository;
import com.leyou.item.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodesServiceImpl implements IGoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecClient specClient;

    @Override
    public SearchResult<Goods> getGoodsListByPage(SearchRequest searchRequest) {

        String key = searchRequest.getKey();

        if (StringUtils.isBlank(key)) {
            return null;
        }

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //控制返回结果字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));
        //查询all字段内是否匹配搜索字段
//        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));

        //添加基础查询
        QueryBuilder basicQuery = bulidBasicQuery(searchRequest);
        queryBuilder.withQuery(basicQuery);

        //排序及分页
        searchWithPageAndSort(searchRequest, queryBuilder);

        //通过品牌及分类进行聚合
        String categoryAggName = "categoryAgg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        String brandAggName = "brandAgg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        List<Map<String, Object>> filterList = new ArrayList<>();

        //获得聚合结果
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
        Aggregations aggs = result.getAggregations();
        //对分类聚合进行处理--category
        List<Long> idList = handleCategoryAgg(filterList, aggs.get(categoryAggName));
        //对品牌聚合进行处理--brand
        handleBrandAgg(filterList, aggs.get(brandAggName));

        //查询参数进行聚合
        if (idList != null && idList.size() == 1) {
            handleSpecAgg(idList,filterList,basicQuery);
        }


        return new SearchResult<>(result.getTotalElements(), result.getTotalPages(), result.getContent(), filterList);
    }

    /**
     * 添加基础查询
     * @param searchRequest
     */
    private QueryBuilder bulidBasicQuery(SearchRequest searchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()));
        Map<String, String> filters = searchRequest.getFilters();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            if (!key.equals("brandId") && !key.equals("cid3")) {
                key = "specs." + key + ".keyword";
            }
            String value = entry.getValue();
            boolQueryBuilder.filter(QueryBuilders.termQuery(key, value));
        }
        return boolQueryBuilder;
    }

    /**
     * 封装查出的参数聚合
     * @param idList
     */
    private void handleSpecAgg(List<Long> idList,List<Map<String, Object>> filterList,QueryBuilder basicQuery) {
        List<SpecParam> specParams = specClient.querySpecParamsByGroupId(null, idList.get(0), true);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(basicQuery);
        queryBuilder.withPageable(PageRequest.of(0, 1));
        for (SpecParam specParam : specParams) {
            String name = specParam.getName();
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs." + name + ".keyword"));
        }
        AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
        Aggregations aggs = result.getAggregations();
        for (SpecParam specParam : specParams) {
            StringTerms aggregation = aggs.get(specParam.getName());
            List<String> options = aggregation.getBuckets().stream().map(b -> b.getKeyAsString())
                    .filter(s -> StringUtils.isNotBlank(s)).collect(Collectors.toList());
            if (options != null && options.size() > 1) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("k", specParam.getName());
                map.put("options", options);
                filterList.add(map);
            }
        }


    }

    /**
     * 封装查出的品牌聚合
     *
     * @param filterList
     * @param brandAgg
     */
    private void handleBrandAgg(List<Map<String, Object>> filterList, LongTerms brandAgg) {
        List<LongTerms.Bucket> buckets = brandAgg.getBuckets();
        List<Long> idList = buckets.stream().map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
        List<Brand> brands = brandClient.queryBrandListByIds(idList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("k", "brandId");
        map.put("options", brands);
        filterList.add(map);
    }

    /**
     * 封装查出的种类聚合
     *
     * @param filterList
     * @param categoryAgg
     */
    private List<Long> handleCategoryAgg(List<Map<String, Object>> filterList, LongTerms categoryAgg) {
        List<LongTerms.Bucket> buckets = categoryAgg.getBuckets();
        List<Long> idList = buckets.stream().map(b -> b.getKeyAsNumber().longValue()).collect(Collectors.toList());
        List<Category> categoryList = categoryClient.queryCategoryListByIds(idList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("k", "cid3");
        map.put("options", categoryList);
        filterList.add(map);
        return idList;
    }

    private void searchWithPageAndSort(SearchRequest searchRequest, NativeSearchQueryBuilder queryBuilder) {
        //是否进行排序
        if (StringUtils.isNotBlank(searchRequest.getSortBy())) {
            queryBuilder.withSort(SortBuilders.fieldSort(searchRequest.getSortBy())
                    .order(searchRequest.getDescending() ? SortOrder.DESC : SortOrder.ASC));
        }
        //查询页数及每页数据量
        queryBuilder.withPageable(PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize()));
    }
}
