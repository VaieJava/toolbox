package com.outdd.toolbox.reptile.thread;

import com.outdd.toolbox.common.util.HttpUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 获取资源线程
 */
public class WriteAccessThread implements Runnable {
    public WriteAccessThread(Queue<Document> queue, ReentrantReadWriteLock rwl, CountDownLatch latch, String url) {
        this.queue = queue;
        this.w = rwl.writeLock();
        this.latch = latch;
        this.url = url;
        this.rwl = rwl;
    }

    Queue<Document> queue;
    Lock w;
    ReentrantReadWriteLock rwl;
    CountDownLatch latch;
    String url;
    String host = null;
    HttpUtils httpUtils = HttpUtils.getInstance();

    @Override
    public void run() {
        write(url);
    }

    /**
     * TODO: 获取网页到队列
     * @param LocalUrl 局部域名
     * @return: void
     * @auther: bjxdts
     * @date: 2018/10/31 14:50
     */
    public void write(String LocalUrl){
        boolean filag = true;
        while (filag) {
            System.out.println("写入开始"+queue.size());

            try {

                w.lock();
                System.out.println("获取写锁");
                Document doc = null;
                try {
                    doc = httpUtils.executeGetAsDocument(LocalUrl);
                    queue.offer(doc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                filag = isNext(doc,LocalUrl);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                w.unlock();
            }
        }
        latch.countDown();
    }

    /**
     * TODO: 获取域名
     * @param url
     * @return: java.lang.String
     * @auther: bjxdts
     * @date: 2018/10/31 15:07
     */
    public String getHost(String url) throws MalformedURLException {

        java.net.URL Url = new java.net.URL(url);
        return "https://" + Url.getHost();// 获取主机名;
    }
    /**
     * TODO: 查询是否有下一章
     * @param doc
     * @return: boolean
     * @auther: bjxdts
     * @date: 2018/10/31 14:52
     */
    public boolean isNext(Document doc,String LocalUrl) throws MalformedURLException {
        boolean filag=true;
        Elements next = doc.select("#j_chapterNext");
        if (next.size() > 0) {
//            LocalUrl = getHost(LocalUrl) + "/0_5/" + next.get(0).attr("href");
           LocalUrl = next.get(0).attr("href");

        } else {
            filag= false;
        }
        return filag;
    }
}
