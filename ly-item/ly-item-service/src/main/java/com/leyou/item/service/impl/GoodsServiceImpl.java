package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import com.leyou.item.service.IGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Spu> querySpu(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(pageBean.getKey())) {
            criteria.andLike("title", "%" + pageBean.getKey() + "%");
        }
        if (pageBean.getSaleable() != null) {
            criteria.andEqualTo("saleable", pageBean.getSaleable());
        }
        criteria.andEqualTo("valid", true);
        example.setOrderByClause(" last_update_time desc");
        List<Spu> spus = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(spus)) {
            throw new LyException(ExceptionEnum.SPU_NOT_FOUND);
        }
        handleCategoryAndBrandName(spus);
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        return new PageResult<>(spuPageInfo.getTotal(), spus);
    }

    private void handleCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            spu.setBname(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
            String collect = categoryMapper.selectByIdList(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.joining("/"));
            spu.setCname(collect);
        }
    }

    @Override
    @Transactional
    public void addGoods(Spu spu) {
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setValid(true);
        spu.setSaleable(true);
        int count = spuMapper.insert(spu);
        Long spu_id = spu.getId();
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_GOODS_SERVER_ERROR);
        }
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu_id);
        count = spuDetailMapper.insert(spuDetail);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_GOODS_SERVER_ERROR);
        }
        addSkusAndStocks(spu);

    }

    private void addSkusAndStocks(Spu spu) {
        Long spu_id =spu.getId() ;
        int count;List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setSpuId(spu_id);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            count = skuMapper.insert(sku);
            if (count != 1) {
                throw new LyException(ExceptionEnum.INSERT_GOODS_SERVER_ERROR);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            count = stockMapper.insert(stock);
            if (count != 1) {
                throw new LyException(ExceptionEnum.INSERT_GOODS_SERVER_ERROR);
            }
        }
    }
}
