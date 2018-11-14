package com.leyou.item.client;

import com.leyou.item.pojo.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("item-service")
public interface BrandClient {
    /**
     * 根据id查询品牌
     *
     * @param id
     * @return
     */
    @GetMapping("brand/id/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);
}
