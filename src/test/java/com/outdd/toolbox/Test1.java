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
import com.outdd.toolbox.reptile.novel.thread.CheckIp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
//    @Autowired
////    @Qualifier("bookCrawlerServiceImpl")
//    CrawlerService<BookInfo> crawlerService = new BookCrawlerServiceImpl();
//    NovelGroupService s = new NovelGroupServiceQiDianImpl();
//    String url="https://www.qidian.com/finish?action=hidden&orderId=&page=1&vip=0&style=1&pageSize=20&siteid=1&pubflag=0&hiddenField=2";
//    @Test
//    public void test1(){
//        NovelService novelService = new VoiceBookNovelServiceImpl();
//        String asd=novelService.getNovelByOne("http://www.audio699.com/book/1759.html","");
//        System.out.println(asd);
////        novelService.getNovelByAll(url);
////        List<Map<String,String>> list = novelService.getNovelByName("阿萨德");
////        for(Map<String,String> map:list){
////            for(String title:map.keySet()){
////                System.out.println(title+""+map.get(title));
////            }
////        }
//    }
//    @Test
//    public void testurl(){
//        //获取代理ip,记得更换，我用的是蘑菇代理的，可以换成其他的网站的
//        String GET_IP_URL = "http://piping.mogumiao.com/proxy/api/get_ip_bs?appKey=6d28338c64364696ac4261e1701775f5&count=10&expiryDate=0&format=1&newLine=2";
//            List<String> addrs = new LinkedList<String>();
//            Map<String,Integer> addr_map = new HashMap<String,Integer>();
//            Map<String,String> ipmap = new HashMap<String,String>();
//            ExecutorService exe = Executors.newFixedThreadPool(10);
//            for (int i=0 ;i<1;i++) {
//                Document doc = null;
//                try {
//                    doc = Jsoup.connect(GET_IP_URL).get();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//                System.out.println(doc.text());
//                JSONObject jsonObject = JSONObject.parseObject(doc.text());
//                List<Map<String,Object>> list = (List<Map<String,Object>>) jsonObject.get("msg");
//                int count = list.size();
//
//                for (Map<String,Object> map : list ) {
//                    String ip = (String)map.get("ip");
//                    String port = (String)map.get("port") ;
//                    ipmap.put(ip,"1");
//                    CheckIp a = new CheckIp(ip, new Integer(port),count);
//                    exe.execute(a);
//                }
//                exe.shutdown();
//                try {
//                    Thread.sleep(1000);
//                }catch (Exception e){
//                    e.printStackTrace();
//
//                }
//            }
//        }
//
//
//    @Test
//    public void test2(){
////        NovelService z=new NovelServiceImplTwo();
////       String d= z.getNovelByOne("https://www.xbiquge6.com/84_84032/","sad");
////        System.out.println(d);
//    }
//    @Test
//    public void test3(){
////        BookInfo bookInfo=crawlerService.crawlInfo("https://book.qidian.com/info/2594975");
////        s.insetNovel("https://book.qidian.com/info/2594975");
////        BookInfo bookInfo=crawlerService.crawlInfo("http://www.pgyzw.com/html/78/78474/index.html");
////        System.out.println(JSONObject.toJSON(bookInfo));
////        NovelServiceImpl asd= new NovelServiceImpl();
////        asd.getNovelByAll("http://www.pgyzw.com/fl/0/1.html");
//    }
}
