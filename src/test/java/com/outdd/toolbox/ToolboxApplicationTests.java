package com.outdd.toolbox;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.service.CrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.outdd.toolbox.**.dao")
public class ToolboxApplicationTests {

    @Autowired
    NovelGroupService novelGroupService;



    @Test
    public void contextLoads() {
        novelGroupService.insetNovel("https://book.qidian.com/info/2594975");

    }
    @Test
    public void query() {

    }

}
