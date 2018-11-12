package com.outdd.toolbox;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.common.util.io.NovelIo;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.CrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.service.impl.BookCrawlerServiceImpl;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImplTwo;
import com.outdd.toolbox.reptile.novel.util.NovelUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Test1 {
    @Autowired
//    @Qualifier("bookCrawlerServiceImpl")
    CrawlerService<BookInfo> crawlerService = new BookCrawlerServiceImpl();
    String url="https://www.qidian.com/finish?action=hidden&orderId=&page=1&vip=0&style=1&pageSize=20&siteid=1&pubflag=0&hiddenField=2";
    @Test
    public void test1(){
        NovelService novelService = new NovelServiceImplTwo();
//        String asd=novelService.getNovelByOne("https://www.yangguiweihuo.com/34/34173/","");
////        System.out.println(asd);
//        ((NovelServiceImplTwo) novelService).getNovelChapter("");
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
        NovelService z=new NovelServiceImplTwo();
       String d= z.getNovelByOne("https://www.xbiquge6.com/84_84032/","sad");
        System.out.println(d);
    }
    @Test
    public void test3(){
//        BookInfo bookInfo=crawlerService.crawlInfo("https://book.qidian.com/info/2594975");
//        BookInfo bookInfo=crawlerService.crawlInfo("https://www.xbiquge6.com/84_84237/");
        System.out.println(CommomUtil.numId());
    }
}
