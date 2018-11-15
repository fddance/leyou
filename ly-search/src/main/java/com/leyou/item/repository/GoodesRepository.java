package com.leyou.item.repository;

import com.leyou.item.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodesRepository extends ElasticsearchRepository<Goods,Long> {
}
