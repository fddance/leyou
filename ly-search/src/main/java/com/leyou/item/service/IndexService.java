package com.leyou.item.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.item.client.BrandClient;
import com.leyou.item.client.CategoryClient;
import com.leyou.item.client.GoodsClient;
import com.leyou.item.client.SpecClient;
import com.leyou.item.common.utils.JsonUtils;
import com.leyou.item.common.utils.NumberUtils;
import com.leyou.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndexService {
    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecClient specClient;

    public Goods bulidGoods(Spu spu) {
        Long spuId = spu.getId();

        String categoryName = categoryClient.queryCategoryListByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                .stream().map(Category::getName).collect(Collectors.joining(" "));
        String brandName = brandClient.queryBrandById(spu.getBrandId()).getName();
        String all = spu.getTitle() + categoryName + brandName;
        List<Sku> skuList = goodsClient.selectSkusBySpuId(spuId);
        Set<Long> priceList = skuList.stream().map(Sku::getPrice).collect(Collectors.toSet());
        List<Map<String, Object>> skus = new ArrayList<>();
        for (Sku sku : skuList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("images",sku.getImages());
            map.put("price", sku.getPrice());
            skus.add(map);
        }


        List<SpecParam> specParams = specClient.querySpecParamsByGroupId(null, spu.getCid3(), true);
        SpuDetail spuDetail = goodsClient.selectGoodsDetail(spuId);
        Map<Long, String> genericSpec = JsonUtils.toMap(spuDetail.getGenericSpec(), Long.class, String.class);
        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });
        Map<String, Object> specs = new HashMap<>();
        for (SpecParam specParam : specParams) {
            String key = specParam.getName();
            Object value = "";
            if (specParam.getGeneric()) {
                value = genericSpec.get(specParam.getId());
                if (specParam.getNumeric()) {
                    chooseSegment(value.toString(), specParam);
                }
            } else {
                value = specialSpec.get(specParam.getId());
            }
            value = value == null ? "其他" : value;
            specs.put(key, value);
        }


        // 构建goods对象
        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spuId);
        goods.setAll(all);// 搜索字段,包含标题,分类,品牌,规格等
        goods.setPrice(priceList);// 所有sku的价格集合
        goods.setSkus(JsonUtils.toString(skus));// 所有sku的集合的json格式
        goods.setSpecs(specs);// 所有的可搜索的规格参数
        goods.setSubTitle(spu.getSubTitle());
        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
