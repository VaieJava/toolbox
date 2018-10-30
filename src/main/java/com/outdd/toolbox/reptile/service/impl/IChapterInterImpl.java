package com.outdd.toolbox.reptile.service.impl;


import com.outdd.toolbox.reptile.pojo.Chapter;
import com.outdd.toolbox.reptile.service.IChapterInter;
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

    protected String crawl(String url) throws Exception {
        //采用HttpClient技术
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(url))) {
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Chapter> getChapter(String url) {
        try {
            String result = crawl(url);
            Document doc = Jsoup.parse(result);
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
    public String getContent(String url){
        String resultContent="";
        try {
            String result = crawl(url);
            Document doc = Jsoup.parse(result);
            Elements as = doc.select("#content");
            List<Chapter> chapters = new ArrayList<>();
            for (Element a : as) {
                resultContent=a.toString();
                resultContent=a.text();
            }
            Elements next = doc.select("#pager_next");
            if(next.size()>0){

                url=next.get(0).attr("href");
                getContent(url);
            }

            return resultContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
