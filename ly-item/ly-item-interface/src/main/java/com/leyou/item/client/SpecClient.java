package com.leyou.item.client;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface SpecClient {

    @GetMapping("spec/params")
    List<SpecParam> querySpecParamsByGroupId(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching

    );

    @GetMapping("spec/all")
    List<SpecGroup> selectAllGroupsAndParamsByCid(@RequestParam("id")Long id);
}
