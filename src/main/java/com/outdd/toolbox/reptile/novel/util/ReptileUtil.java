package com.outdd.toolbox.reptile.novel.util;

import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.common.util.HttpUtils;
import com.outdd.toolbox.common.util.io.NovelIo;
import lombok.Data;
import org.apache.http.protocol.RequestTargetHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/*
 * TODO: 爬虫工具类
 * @author VAIE
 * @date: 2018/11/2-22:22
 * @version v1.0
 */
@Data
public class ReptileUtil {
    public static HttpUtils httpUtils = HttpUtils.getInstance();
    public static NovelIo no = new NovelIo();//小说io类


    /**
     * TODO: 根据url获取Document
     *
     * @param url
     * @param lock lock锁
     * @return: org.jsoup.nodes.Document
     * @auther: vaie
     * @date: 2018/11/2 22:16
     */
    public static Document getDocumentOfHttps(String url, Lock lock) {

        Document document = null;
        lock.lock();//获取写锁
        try {
            document = httpUtils.executeGetWithSSLAsDocument(url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//释放写锁
        }
        return document;
    }

    /**
     * TODO: 获取Document通过HTTPS
     *
     * @param url 网络地址
     * @return: org.jsoup.nodes.Document
     * @auther: vaie
     * @date: 2018/11/2 21:10
     */
    public static Document getDocumentOfHttps(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(9000)
                    .post();
//            document =httpUtils.executeGetWithSSLAsDocument(url);
//            Jsoup.parse(new URL(url).openStream(), "GBK", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * TODO: 获取域名
     *
     * @param url
     * @return: java.lang.String
     * @auther: bjxdts
     * @date: 2018/10/31 15:07
     */
    public static String getHost(String url) throws MalformedURLException {

        java.net.URL Url = new java.net.URL(url);
        return "https://" + Url.getHost();// 获取主机名;
    }

    public static String getUrl(Document doc){
        return  doc.select("a").get(0).absUrl("href");
    }


    /**
     * TODO: 查询是否有下一章
     * @param doc
     * @param nextName 下一章的规则id民或者class名
     * @return: String url - boolen filag 需要强制转换
     * @auther: bjxdts
     * @date: 2018/10/31 14:52
     */
    public static Map<String, Object> isNext(Document doc, String nextName) throws MalformedURLException {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean filag = true;
        Elements next = doc.select(nextName);
        if (next.size() > 0) {
            map.put("url", next.get(0).attr("href"));
        } else {
            filag = false;
        }
        map.put("filag", filag);
        return map;
    }

    /**
     * TODO: 获取小说内容
     * @param doc Document
     * @param titleRule 标题规则
     * @param contentsRule 内容规则
     * @return: java.lang.String
     * @auther: vaie
     * @date: 2018/11/2 20:58
     */
    public static String getDetails(Document doc,String titleRule,String contentsRule){
        StringBuffer resultContent = new StringBuffer();
        Elements titles = doc.select(titleRule);//标题
        Elements contents = doc.select(contentsRule);//内容
        String vUrl = "";
        String vContent = "";
        for (Element title : titles) {
            vUrl = title.text();
            resultContent.append(vUrl + "\r\n");
        }
        for (Element content : contents) {
            vContent = content.attr("src").replaceAll(" ", "\r\n");
            resultContent.append(vContent + "\r\n");
        }
        try{
            ReptileUtil.downLoadFromUrl(vContent,
                    vUrl+".mp3","D:/shengxu/");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return resultContent.toString();
    }

    /**
     * TODO: 爬取小说内容信息
     *
     * @param doc Document类
     * @return: NovelVolume 内容类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public static String getContent(Document doc,String Rule) {
        String content="";
        if (CommomUtil.isNotNull(doc)) {
            try {
                Element contents = doc.select(Rule).get(0);//内容
                content=(contents.text().replaceAll(" ", "\r\n") + "\r\n");
            }catch (Exception e){

            }

        }
        return content;
    }
    /**
     * TODO: 爬取小说内容信息
     *
     * @param url Document类
     * @return: NovelVolume 内容类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public static String getContent(String  url,String Rule) {

        return getContent(ReptileUtil.getDocumentOfHttps(url),Rule);
    }

    /**
     * TODO: 通过地址获取目录标题和地址
     * @param url 网络地址
     * @param titleRule 标题规则
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @auther: vaie
     * @date: 2018/11/3 17:59
     */
    public static List<Map<String,String>> getNovelByUrlToList(String url,String titleRule){
        Document doc = getDocumentOfHttps(url);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (CommomUtil.isNotNull(doc)) {
            Elements titleUrls = doc.select(titleRule);//标题
            for (Element titleUrl : titleUrls) {
                Map<String,String> map = new HashMap<String,String>();
                map.put("title", titleUrl.text());
                map.put("url", "https:" + titleUrl.attr("href"));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * TODO: 通过地址获取目录标题和地址
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @auther: vaie
     * @date: 2018/11/3 17:59
     */
    public static List<Map<String,String>> getNovelByUrlToList(String url){
        Document doc = getDocumentOfHttps(url);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (CommomUtil.isNotNull(doc)) {
            Elements lis = doc.select(".res-book-item");//li
            for (Element li : lis) {
                Map<String,String> map = new HashMap<String,String>();
                Elements infos = li.select(".book-mid-info");//div
                for(Element info : infos){
                    Elements urlAndTitles =info.select("h4 a");
                    for(Element urlAndTitle : urlAndTitles){
                        map.put("title", urlAndTitle.text());
                        map.put("url", "https:" + urlAndTitle.attr("href"));
                    }
                    Elements authors =info.select(".author");
                    for(Element author : authors){
                        Elements names =info.select(".name");
                        for(Element name : names){
                            map.put("aout", name.text());
                        }
                    }
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     * TODO: 通过已解析的网页文件获取目录标题和地址
     * @param doc 已解析的网页文件
     * @param rule 规则
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @auther: vaie
     * @date: 2018/11/3 17:59
     */
    public static List<Map<String,String>> getNovelByUrlToList(Document doc,String rule){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (CommomUtil.isNotNull(doc)) {
            Elements titleUrls = doc.select(rule);//标题

            for (Element titleUrl : titleUrls) {
                Map<String,String> map = new HashMap<String,String>();
                map.put("title", titleUrl.text());
                map.put("url", titleUrl.absUrl("href"));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * TODO: 获取全部目录转为Document
     * @param title 标题
     * @param url 网址
     * @param directoryRule 规则
     * @return: java.util.List<org.jsoup.nodes.Document>
     * @auther: vaie
     * @date: 2018/11/4 17:21
     */
    public static List<Document> getDirectoryAll(String url,String title,String directoryRule) {
        Document doc = ReptileUtil.getDocumentOfHttps(url);

        List<Document> fdList = null;
        if (CommomUtil.isNotNull(doc)) {
            Elements titleUrls = doc.select(directoryRule);//标题
            fdList = new ArrayList<Document>();
            String host= null;
            try {
                host = getHost(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            for (Element titleUrl : titleUrls) {
//                String urll=host+"" + titleUrl.attr("href");
                String urll=titleUrl.attr("href");
                Document document = ReptileUtil.getDocumentOfHttps(urll);
                if(CommomUtil.isNotNull(document)){
                    fdList.add(document);
                }

            }
        }
        return fdList;
    }

    /**
     * TODO: 把Document转换为text
     * @param fdList
     * @param titleRule 标题规则
     * @param contentsRule 内容规则
     * @return: void
     * @auther: vaie
     * @date: 2018/11/4 17:37
     */
    public static String DocumentToTxt(List<Document> fdList,String titleRule,String contentsRule){
        StringBuilder Details=new StringBuilder();
        for (Document doc:fdList){
            if(CommomUtil.isNotNull(doc)){
                Details.append(ReptileUtil.getDetails(doc,titleRule,contentsRule));
                System.out.println(Details.toString());
            }
        }
        return Details.toString();
    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success");

    }



    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


}
