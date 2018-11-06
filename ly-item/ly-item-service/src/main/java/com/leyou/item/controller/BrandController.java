package com.leyou.item.controller;

import com.leyou.item.common.vo.PageBean;
import com.leyou.item.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(PageBean pageBean) {
        return ResponseEntity.ok(brandService.queryBrandByPage(pageBean));
    }
}
