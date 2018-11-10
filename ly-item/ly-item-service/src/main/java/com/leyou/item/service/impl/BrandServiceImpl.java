package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.IBrandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPage(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPage(), pageBean.getRows());
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(pageBean.getKey())) {
            example.createCriteria().andLike("name", "%" + pageBean.getKey() + "%")
                    .orEqualTo("letter",pageBean.getKey());
        }
        if (StringUtils.isNotBlank(pageBean.getSortBy())) {
            String orderByClause = pageBean.getSortBy() + (pageBean.getDesc() ? " desc " : " asc ");
            example.setOrderByClause(orderByClause);
        }
        List<Brand> brands = brandMapper.selectByExample(example);
        log.warn(brands.toString());
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return new PageResult<>(brandPageInfo.getTotal(),brandPageInfo.getList());
    }

    @Override
    @Transactional
    public void addBrand(Brand brand, List<Long> cidList) {
        int count = brandMapper.insert(brand);
        if (count == 0) {
            throw new LyException(ExceptionEnum.INSERT_BRAND_SERVER_ERROR);
        }
        for (Long cid : cidList) {
            int i = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (i == 0) {
                throw new LyException(ExceptionEnum.INSERT_BRAND_CATEGORY_SERVER_ERROR);
            }
        }
    }
}
