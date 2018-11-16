package com.leyou.item.pojo;

import com.leyou.item.common.vo.PageResult;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SearchResult<T> extends PageResult<T> {
    private List<Map<String, Object>> filterList;

    public SearchResult() {
    }

    public SearchResult(Long total, List<T> items, List<Map<String, Object>> filterList) {
        super(total, items);
        this.filterList = filterList;
    }

    public SearchResult(Long total, Integer totalPage, List<T> items, List<Map<String, Object>> filterList) {
        super(total, totalPage, items);
        this.filterList = filterList;
    }
}
