package com.leyou.item.common.vo;

import lombok.Data;

@Data
public class PageBean {
    /*key:this.search,
    page:this.pagination.page,
    rows:this.pagination.rowsPerPage,
    sortBy:this.pagination.sortBy,
    desc:this.pagination.descending*/
    private String key=null;
    private Integer page=1;
    private Integer rows=5;
    private String sortBy=null;
    private Boolean desc=false;
}
