package com.outdd.toolbox.reptile.service.impl;


import com.outdd.toolbox.common.util.HttpUtils;
import com.outdd.toolbox.reptile.thread.ReadAccessThread;
import com.outdd.toolbox.reptile.thread.WriteAccessThread;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class IChapterInterImpl {

    protected  String host = null;

    HttpUtils httpUtils =HttpUtils.getInstance();
    int cs=3;
    int count=0;
    StringBuffer resultContent=new StringBuffer();
    public String getContent(String url){
        Elements titles=null;
        try {
//            String resl=crawl(url);
//            Document absdoc = Jsoup.connect(url).get();
//            Document doc = Jsoup.parse(resl);
            if(host==null){
                java.net.URL  urlz = new  java.net.URL(url);

                host = "https://"+urlz.getHost();// 获取主机名
            }
            Document doc =null;
            try {
               doc = httpUtils.executeGetAsDocument(url);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            titles = doc.select(".bookname h1");
            Elements contents = doc.select("#content");//内容
            for (Element title:titles){
                System.out.println("正在爬取"+title.text());
                resultContent.append(title.text()+"\r\n");
            }
            for (Element content:contents){
                resultContent.append(content.text().replaceAll(" " , "\r\n")+"\r\n");
            }
            //查询是否有下一章
            Elements next = doc.select("#pager_next");
            if(next.size()>0){

                url=host+"/0_5/"+next.get(0).attr("href");
                getContent(url);
            }else{
                url=host+"/0_5/"+next.get(0).attr("href");
                if(count>cs){
                    System.out.println("爬取完成");
                }else{
                    count++;
                    System.out.println("爬取错误，正在尝试第"+count+"次");
                    getContent(url);
                }

            }
        } catch (Exception e) {
            System.out.println(url);
            System.out.println("正在爬取"+titles.text()+"出错");
            resultContent.append("出错了");
            e.printStackTrace();
        }
        return resultContent.toString();
    }

    public void asd(String url){
        int xiet=2;
         CountDownLatch latch=new CountDownLatch(xiet);//两个工人的协作
        Queue<Document> queue = new LinkedList<Document>();
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

            new Thread(new WriteAccessThread(queue,rwl,latch,url)).start();
            new Thread(new ReadAccessThread(queue,rwl,latch)).start();



        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束了");
    }

}
