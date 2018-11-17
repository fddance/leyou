package com.leyou.item.service.impl;

import com.leyou.item.client.BrandClient;
import com.leyou.item.client.CategoryClient;
import com.leyou.item.client.GoodsClient;
import com.leyou.item.client.SpecClient;
import com.leyou.item.pojo.*;
import com.leyou.item.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PageServiceImpl implements IPageService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecClient specClient;

    @Override
    public Map<String, Object> getData(Long id) {
//        - spu信息
        Spu spu = goodsClient.selectSpuById(id);
//        - spu的详情
        SpuDetail spuDetail = goodsClient.selectGoodsDetail(id);
//        - spu下的所有sku
        List<Sku> skus = goodsClient.selectSkusBySpuId(id);
//        - 品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
//        - 商品三级分类
        List<Category> categoryList = categoryClient.queryCategoryListByIds
                (new ArrayList<>(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())));
//        - 商品规格参数、规格参数组
        List<SpecGroup> specs = specClient.selectAllGroupsAndParamsByCid(spu.getCid3());
        HashMap<String, Object> data = new HashMap<>();
        data.put("specs", specs);
        data.put("brand", brand);
        data.put("categories", categoryList);
        data.put("skus", skus);
        data.put("detail", spuDetail);
        data.put("spu", spu);
        return data;
    }
}
