package com.outdd.toolbox.reptile.service.impl;


import com.outdd.toolbox.common.util.HttpUtils;
import com.outdd.toolbox.reptile.pojo.Chapter;
import com.outdd.toolbox.reptile.service.IChapterInter;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class IChapterInterImpl implements IChapterInter {

    protected  String host = null;
    HttpUtils httpUtils =HttpUtils.getInstance();

    protected String crawl(String url) {

        String result="";
        //采用HttpClient技术
        try{
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            // 创建httpget实例
            HttpGet httpget = new HttpGet(url);
            // 模拟浏览器 ✔
            httpget.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

            // 使用代理 IP ✔
            HttpHost proxy = new HttpHost("117.191.11.74", 8080);
            RequestConfig config = RequestConfig.custom().setProxy(proxy)
                    //设置连接超时 ✔
                    .setConnectTimeout(10000) // 设置连接超时时间 10秒钟
                    .setSocketTimeout(10000) // 设置读取超时时间10秒钟
                    .build();

            httpget.setConfig(config);

             CloseableHttpResponse httpResponse = httpClient.execute(httpget);
            if(host==null){
                java.net.URL  urlz = new  java.net.URL(url);

                host = "https://"+urlz.getHost();// 获取主机名
            }
            result = EntityUtils.toString(httpResponse.getEntity());

        } catch (Exception e) {
            System.out.println("adas");
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Chapter> getChapter(String url) {
        try {
            String result = crawl(url);
            Document doc = Jsoup.parse(url);
            Elements as = doc.select("#list dd a");
            List<Chapter> chapters = new ArrayList<>();
            for (Element a : as) {
                Chapter chapter = new Chapter();
                chapter.setTitle(a.text());
                chapter.setUrl("http://www.bxwx8.org" + a.attr("href"));
                chapters.add(chapter);
                break;
            }
            return chapters;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
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

}
