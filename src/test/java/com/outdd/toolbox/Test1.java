package com.outdd.toolbox;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.common.util.io.NovelIo;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.NovelCrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.service.impl.NovelCrawlerServiceQiDianImpl;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImplTwo;
import com.outdd.toolbox.reptile.novel.util.NovelUtil;
import org.junit.Test;

public class Test1 {
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
        NovelCrawlerService ncs=new NovelCrawlerServiceQiDianImpl();

        BookInfo bi=ncs.crawlNovelDetails("https://book.qidian.com/info/1011646859");
        StringBuilder sb = new StringBuilder();
        sb.append(bi.getBookName()+"\r\n");
        for(VolumeInfo vi:bi.getVolumeInfos()){
            sb.append(vi.getVolumeName()+"\r\n");
            for(ChapterInfo ci:vi.getChapterInfos()){
                sb.append(ci.getChapterName()+"\r\n");
                sb.append(new String(ci.getContent())+"\n\r");
            }
        }
        NovelIo no=new NovelIo();
//        System.out.println(sb.toString());
        System.out.println(JSONObject.toJSON(bi));
//        no.write(sb.toString(),bi.getBookName()+"_"+bi.getAuthor());
    }
    @Test
    public void test3(){

    }
}
