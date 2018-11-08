package com.outdd.toolbox;

import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImpl;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImplTwo;
import org.junit.Test;

public class Test1 {
    String url="https://www.qidian.com/finish?action=hidden&orderId=&page=1&vip=0&style=1&pageSize=20&siteid=1&pubflag=0&hiddenField=2";
    @Test
    public void test1(){
        NovelService novelService = new NovelServiceImplTwo();
        String asd=novelService.getNovelByOne("https://www.yangguiweihuo.com/34/34173/","");
        System.out.println(asd);
//        novelService.getNovelByAll(url);
//        List<Map<String,String>> list = novelService.getNovelByName("阿萨德");
//        for(Map<String,String> map:list){
//            for(String title:map.keySet()){
//                System.out.println(title+""+map.get(title));
//            }
//        }
    }

}
