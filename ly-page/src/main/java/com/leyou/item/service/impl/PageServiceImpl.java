package com.leyou.item.service.impl;

import com.leyou.item.client.BrandClient;
import com.leyou.item.client.CategoryClient;
import com.leyou.item.client.GoodsClient;
import com.leyou.item.client.SpecClient;
import com.leyou.item.pojo.*;
import com.leyou.item.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
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

    @Autowired
    private TemplateEngine templateEngine;

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
        data.put("title", spu.getTitle());
        data.put("subTitle", spu.getSubTitle());
        return data;
    }

    public void creatItemHtml(Long spuId) {
        Context context = new Context();
        context.setVariables(getData(spuId));
        File file = getFilePath(spuId);
        try(PrintWriter printWriter=new PrintWriter(file,"UTF-8")) {
            templateEngine.process("item", context, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getFilePath(Long spuId) {
        File file = new File("E:\\nginx\\nginx-1.13.8\\html\\item");
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, spuId + ".html");
    }


}
