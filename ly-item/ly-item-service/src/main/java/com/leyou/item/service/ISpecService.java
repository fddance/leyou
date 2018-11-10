package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

public interface ISpecService {
    /**
     * 根据商品品牌id查询详情组
     * @param cid
     * @return
     */
    List<SpecGroup> querySpecGroupsByCid(Long cid);

    /**
     * 根据商品详情组id查询详情字段
     * @param gid
     * @return
     */
    List<SpecParam> querySpecParamsByGroupId(Long gid);
}
