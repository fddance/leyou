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
     * 根据商品详情组id,或者种类id,或者是否查询字段查询详情字段
     *
     * @param gid
     * @param cid
     * @param searching
     * @return
     */
    List<SpecParam> querySpecParamsByGroupId(Long gid, Long cid, Boolean searching);

    /**
     * 修改商品详情组信息
     * @param specGroup
     */
    void updateSpecGroup(SpecGroup specGroup);

    /**
     * 添加商品详情组信息
     * @param specGroup
     */
    void addSpecGroup(SpecGroup specGroup);

    /**
     * 删除商品详情组信息
     * @param gid
     */
    void deleteSpecGroupsByGid(Long gid);

    /**
     * 添加商品详情参数信息
     * @param specParam
     */
    void addSpecParam(SpecParam specParam);

    /**
     * 修改商品详情参数信息
     * @param specParam
     */
    void updateSpecParam(SpecParam specParam);

    /**
     * 根据商品详情参数id删除商品详情参数信息
     * @param pid
     */
    void deleteSpecParamByPid(Long pid);
}
