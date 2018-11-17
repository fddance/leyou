package com.leyou.item.cotroller;

import com.leyou.item.common.utils.JsonUtils;
import com.leyou.item.service.IPageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
public class PageController {

    @Autowired
    private IPageService pageService;

    @GetMapping("item/{id}.html")
    public String toItemPage(Model model,@PathVariable("id")Long id) {
        Map<String, Object> data = pageService.getData(id);
        String s = JsonUtils.toString(data);
        System.out.println("s = " + s);
        model.addAllAttributes(data);
        return "item";
    }
}

