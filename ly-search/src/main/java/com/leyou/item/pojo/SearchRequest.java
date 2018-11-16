package com.leyou.item.pojo;

import java.util.Map;

public class SearchRequest {
    //搜索字段
    private String key;
    //当前页数
    private Integer page;
    //排序字段
    private String sortBy;
    //是否降序
    private Boolean descending;
    //筛选字段
    private Map<String, String> filters;

    private static final Integer DEFAULT_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        page = Math.max(DEFAULT_PAGE, page);
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }

}
