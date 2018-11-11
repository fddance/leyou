package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    /**
     * 向品牌和类型中间表中添加数据
     * @param cid   类型id
     * @param bid   品牌id
     * @return
     */
    @Insert("insert into tb_category_brand values (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);

    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    void deleteCids(@Param("bid") Long id);

    @Select("select b.* from tb_brand b inner join tb_category_brand cb on cb.brand_id = b.id\n" +
            "where cb.category_id = #{cid} ")
    List<Brand> queryByBrandByCid(@Param("cid") Long cid);
}
