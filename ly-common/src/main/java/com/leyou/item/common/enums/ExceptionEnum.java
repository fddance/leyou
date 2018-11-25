package com.leyou.item.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOUND(404, "商品静态资源未找到"),
    SPECGROUP_NOT_FOUND(404, "商品详情资源未找到"),
    SPU_NOT_FOUND(404, "spu详情资源未找到"),
    SKU_LIST_NOT_FOUND(404, "spu详情资源未找到"),
    BRAND_NOT_FOUND(404, "品牌静态资源未找到"),
    GOODS_NOT_FOUND(404, "商品详情未找到"),
    INSERT_BRAND_SERVER_ERROR(500, "新增品牌失败"),
    INSERT_BRAND_CATEGORY_SERVER_ERROR(500,"新增品牌种类时失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    UPLOAD_IMAGE_ERROR(500, "上传文件错误"),
    UPDATE_BRAND_SERVER_ERROR(500, "修改品牌失败"),
    UPDATE_SPECGROUP_SERVER_ERROR(500, "修改品牌失败"),
    UPDATE_BRAND_CATEGORY_SERVER_ERROR(500,"新增品牌种类时失败"),
    DELETE_BRAND_SERVER_ERROR(500, "新增品牌种类时失败"),
    INSERT_GOODS_SERVER_ERROR(500, "新增商品详情时失败"),
    UPDATE_GOODS_SERVER_ERROR(500, "修改商品详情时失败"),
    INSERT_USER_SERVER_ERROR(500, "修改商品详情时失败"),
    CART_SERVER_ERROR(500, "购物车出现错误"),
    BID_CANNOT_BE_NULL(400,"bid不能为空"),
    INVALID_PARAM_TYPE(400,"无效参数类型"),
    INVALID_USERNAME_OR_PASSWORD(400, "用户名或密码错误"),
    CUSTOM_ERROR(0, "未知错误"),
    ;

    private Integer status;
    private String msg;

    public ExceptionEnum init(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        return this;
    }

}
