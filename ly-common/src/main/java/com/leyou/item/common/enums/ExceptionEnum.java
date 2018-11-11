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
    BRAND_NOT_FOUND(404, "品牌静态资源未找到"),
    INSERT_BRAND_SERVER_ERROR(500, "新增品牌失败"),
    INSERT_BRAND_CATEGORY_SERVER_ERROR(500,"新增品牌种类时失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    UPLOAD_IMAGE_ERROR(500, "上传文件错误"),
    UPDATE_BRAND_SERVER_ERROR(500, "修改品牌失败"),
    UPDATE_SPECGROUP_SERVER_ERROR(500, "修改品牌失败"),
    UPDATE_BRAND_CATEGORY_SERVER_ERROR(500,"新增品牌种类时失败"),
    DELETE_BRAND_SERVER_ERROR(500, "新增品牌种类时失败"),
    BID_CANNOT_BE_NULL(400,"bid不能为空"),
    ;

    private Integer status;
    private String msg;

}
