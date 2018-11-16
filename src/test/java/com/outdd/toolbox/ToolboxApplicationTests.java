package com.outdd.toolbox;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.ui.novel.service.NovelServiceUi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.outdd.toolbox.**.dao")
public class ToolboxApplicationTests {

    @Autowired
    NovelGroupService novelGroupService;

    @Autowired
    NovelService novelService;


    @Test
    public void contextLoads() {
//        novelService.getNovelByAll("http://www.pgyzw.com/fl/0/1.html");
//        novelGroupService.insetNovel("http://www.pgyzw.com/html/78/78474/index.html");

    }
    @Test
    public void query() {
//        PageInfo<BookInfo> b=new PageInfo<BookInfo>();
//        b.setPageSize(2);
//        b.setPageNum(2);
//        PageInfo<BookInfo> bi=novelServiceUi.getBookInfo(b);

//        System.out.println(JSONObject.toJSON(bi.getList()));
    }

}
