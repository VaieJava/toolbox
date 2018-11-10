package com.outdd.toolbox;

import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImpl;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImplTwo;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class Test1 {
    String url="https://www.qidian.com/finish?action=hidden&orderId=&page=1&vip=0&style=1&pageSize=20&siteid=1&pubflag=0&hiddenField=2";
    @Test
    public void test1(){
        NovelService novelService = new NovelServiceImplTwo();
//        String asd=novelService.getNovelByOne("https://www.yangguiweihuo.com/34/34173/","");
////        System.out.println(asd);url="";
        Document doc =ReptileUtil.getDocumentOfHttps("https://book.qidian.com/info/1010734492");
        ((NovelServiceImplTwo) novelService).getNovelVolume(doc);
//        novelService.getNovelByAll(url);
//        List<Map<String,String>> list = novelService.getNovelByName("阿萨德");
//        for(Map<String,String> map:list){
//            for(String title:map.keySet()){
//                System.out.println(title+""+map.get(title));
//            }
//        }
    }

}
