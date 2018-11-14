package com.leyou.item;

import com.leyou.item.client.BrandClient;
import com.leyou.item.client.CategoryClient;
import com.leyou.item.client.GoodsClient;
import com.leyou.item.client.SpecClient;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.SpuDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

}