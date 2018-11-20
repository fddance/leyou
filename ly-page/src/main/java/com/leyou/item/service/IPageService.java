package com.leyou.item.service;

import java.util.Map;

public interface IPageService {
    Map<String,Object> getData(Long id);

    void createHtml(Long id);

    void deleteHtml(Long id);
}
