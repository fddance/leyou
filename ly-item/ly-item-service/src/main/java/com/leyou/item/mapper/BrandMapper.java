package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 向品牌和类型中间表中添加数据
     * @param cid   类型id
     * @param bid   品牌id
     * @return
     */
    @Insert("insert into tb_category_brand values (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);
}
