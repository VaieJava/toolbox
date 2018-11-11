package com.outdd.toolbox;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.reptile.novel.dao.BookInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.ChapterInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.VolumeInfoMapper;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.NovelCrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.outdd.toolbox.**.dao")
public class ToolboxApplicationTests {

    @Autowired
    NovelGroupService novelGroupService;



    @Test
    public void contextLoads() {
        novelGroupService.insetNovel("https://book.qidian.com/info/1013305105");

    }
    @Test
    public void query() {

    }

}
