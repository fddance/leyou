package com.leyou.item.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOUND(404, "商品静态资源未找到"),
    ;

    private Integer status;
    private String msg;

}
