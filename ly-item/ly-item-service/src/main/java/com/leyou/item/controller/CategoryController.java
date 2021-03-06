package com.leyou.item.controller;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("list")
    public ResponseEntity<List<Category>> queryCategoryList(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        return ResponseEntity.ok(categoryService.queryCategoryList(pid));
    }

    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable("bid") Long bid) {
        return ResponseEntity.ok(categoryService.queryCategoryByBid(bid));
    }

    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryListByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(categoryService.queryCategoryListByIds(ids));
    }

    @GetMapping("cname")
    public ResponseEntity<List<String>> getCnameListByCid3(@RequestParam("cid3") Long cid3) {
        return ResponseEntity.ok(categoryService.queryCnameListByCid3(cid3));
    }
}
