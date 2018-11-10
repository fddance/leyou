package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.ISpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private ISpecService specService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupsByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specService.querySpecGroupsByCid(cid));
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParamsByGroupId(@RequestParam("gid") Long gid) {
        return ResponseEntity.ok(specService.querySpecParamsByGroupId(gid));
    }
}
