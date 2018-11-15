package com.leyou.item;

import com.leyou.item.client.BrandClient;
import com.leyou.item.client.CategoryClient;
import com.leyou.item.client.GoodsClient;
import com.leyou.item.client.SpecClient;
import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.*;
import com.leyou.item.repository.GoodesRepository;
import com.leyou.item.service.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTest {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecClient specClient;

    @Autowired
    private IndexService indexService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodesRepository goodesRepository;

    @Test
    public void method() {
        Brand brand = brandClient.queryBrandById(2032l);
        System.out.println("brand = " + brand);
    }

    @Test
    public void method2() {
        List<Category> categoryList = categoryClient.queryCategoryListByIds(Arrays.asList(2l, 3l, 4l, 5l));
        for (Category category : categoryList) {
            System.out.println("category = " + category);
        }
    }

    @Test
    public void method3() {
        List<SpecParam> specParams = specClient.querySpecParamsByGroupId(null, 76l, null);
        for (SpecParam specParam : specParams) {
            System.out.println("specParam = " + specParam);
        }
    }

    @Test
    public void method4() {
        SpuDetail spuDetail = goodsClient.selectGoodsDetail(2l);
        System.out.println("spuDetail = " + spuDetail);
    }

    @Test
    public void method5() {
        int page = 0;
        int rows = 100;
        int size=0;
        PageBean pageBean1 = new PageBean();
        pageBean1.setPage(page);
        pageBean1.setRows(rows);
        pageBean1.setSaleable(true);
        PageResult<Spu> spuPageResult = goodsClient.querySpu(pageBean1);
        List<Spu> items = spuPageResult.getItems();
        System.out.println("items = " + items);
    }

    @Test
    public void loadData() {
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        int page = 0;
        int rows = 100;
        int size=0;
        do {
            try {
                PageBean pageBean = new PageBean();
                pageBean.setPage(page);
                pageBean.setRows(rows);
                pageBean.setSaleable(true);
                PageResult<Spu> spuPageResult = goodsClient.querySpu(pageBean);
                List<Spu> items = spuPageResult.getItems();
                System.out.println("items = " + items);
                size = items.size();
                List<Goods> collect = items.stream().map(indexService::bulidGoods).collect(Collectors.toList());
                System.out.println("collect = " + collect);
                goodesRepository.saveAll(collect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (size == rows);
    }

}