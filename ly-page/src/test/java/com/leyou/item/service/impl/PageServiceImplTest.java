package com.leyou.item.service.impl;

import com.leyou.item.service.IPageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageServiceImplTest {

    @Autowired
    private PageServiceImpl pageService;

    @Test
    public void creatItemHtml() {
        for (int i = 2; i < 183; i++) {
            pageService.createHtml((long) i);
        }
    }
}