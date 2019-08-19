package com.outdd.toolbox;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.common.util.io.NovelIo;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.CrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.service.impl.*;
import com.outdd.toolbox.reptile.novel.util.NovelUtil;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;

public class Test1 {
    @Autowired
//    @Qualifier("bookCrawlerServiceImpl")
    CrawlerService<BookInfo> crawlerService = new BookCrawlerServiceImpl();
    NovelGroupService s = new NovelGroupServiceQiDianImpl();
    String url="https://www.qidian.com/finish?action=hidden&orderId=&page=1&vip=0&style=1&pageSize=20&siteid=1&pubflag=0&hiddenField=2";
    @Test
    public void test1(){
        NovelService novelService = new VoiceBookNovelServiceImpl();
        String asd=novelService.getNovelByOne("http://www.audio699.com/book/1730.html","");
        System.out.println(asd);
//        novelService.getNovelByAll(url);
//        List<Map<String,String>> list = novelService.getNovelByName("阿萨德");
//        for(Map<String,String> map:list){
//            for(String title:map.keySet()){
//                System.out.println(title+""+map.get(title));
//            }
//        }
    }

    @Test
    public void test2(){
//        NovelService z=new NovelServiceImplTwo();
//       String d= z.getNovelByOne("https://www.xbiquge6.com/84_84032/","sad");
//        System.out.println(d);
    }
    @Test
    public void test3(){
//        BookInfo bookInfo=crawlerService.crawlInfo("https://book.qidian.com/info/2594975");
//        s.insetNovel("https://book.qidian.com/info/2594975");
//        BookInfo bookInfo=crawlerService.crawlInfo("http://www.pgyzw.com/html/78/78474/index.html");
//        System.out.println(JSONObject.toJSON(bookInfo));
//        NovelServiceImpl asd= new NovelServiceImpl();
//        asd.getNovelByAll("http://www.pgyzw.com/fl/0/1.html");
    }
}
