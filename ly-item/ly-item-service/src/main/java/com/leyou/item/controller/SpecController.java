package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.ISpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup) {
        specService.updateSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("group")
    public ResponseEntity<Void> addSpecGroup(@RequestBody SpecGroup specGroup) {
        specService.addSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("group/{gid}")
    public ResponseEntity<Void> deleteSpecGroupsByGid(@PathVariable("gid") Long gid) {
        specService.deleteSpecGroupsByGid(gid);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParamsByGroupId(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching

    ) {
        return ResponseEntity.ok(specService.querySpecParamsByGroupId(gid, cid, searching));
    }

    @PostMapping("param")
    public ResponseEntity<Void> addSpecParam(@RequestBody SpecParam specParam) {
        specService.addSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam) {
        specService.updateSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("param/{pid}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("pid") Long pid) {
        specService.deleteSpecParamByPid(pid);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("all")
    public ResponseEntity<List<SpecGroup>> selectAllGroupsAndParamsByCid(@RequestParam("id")Long id) {
        return ResponseEntity.ok(specService.selectAllGroupsAndParamsByCid(id));
    }
}
