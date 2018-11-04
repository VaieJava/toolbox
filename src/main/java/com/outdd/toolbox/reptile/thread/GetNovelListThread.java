package com.outdd.toolbox.reptile.thread;

import com.outdd.toolbox.common.util.HttpUtils;
import com.outdd.toolbox.reptile.pojo.NovelAssist;
import com.outdd.toolbox.reptile.util.ReptileUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * 获取小说列表线程
 */
@Slf4j
public class GetNovelListThread implements Runnable {
    public GetNovelListThread(NovelAssist na) {
        this.NovelList = na.getNovelList();
        this.w = na.getListRwl().writeLock();
        this.latch = na.getLatch();
        this.url = na.getUrl();
        this.listRule = na.getListRule();
        this.nextRule = na.getNextRule();
    }

    Lock w;//写锁
    CountDownLatch latch;//信号量
    String url;//网络地址
    String listRule;
    String nextRule;

    HttpUtils httpUtils = HttpUtils.getInstance();
    Queue<List<Map<String, String>>> NovelList;

    @Override
    public void run() {
        getAllNovelList(url, listRule, nextRule);
    }

    /**
     * TODO: 获取全部小说列表
     *
     * @param url
     * @param listRule 列表规则
     * @param nextRule 下一章规则
     * @return: java.util.Queue<java.util.Map               <               java.lang.String               ,               java.lang.String>>
     * @auther: vaie
     * @date: 2018/11/1 21:05
     */
    public void getAllNovelList(String url, String listRule, String nextRule) {
        boolean filag = true;
        try {
            log.info(Thread.currentThread().getName()+"开启");
            while (filag) {
                Document doc = ReptileUtil.getDocumentOfHttps(url);
                    w.lock();//上了局部写锁
                    NovelList.offer(ReptileUtil.getNovelByUrlToList(doc,listRule));
                System.out.println("cg");
                    w.unlock();
                    Map<String, Object> map = ReptileUtil.isNext(doc, nextRule);
                    filag = (Boolean) map.get("filag");
                    url = (String) map.get("url");


                while (NovelList.size() > 500){
                    log.info("NovelList过大。获取小说列表休眠10分钟");
                    Thread.sleep(1000*60*30);
                }

            }
            log.info("获取列表完成");
        } catch (Exception e) {
            log.error("获取列表异常");
            e.printStackTrace();
        } finally {
            w.unlock();
            log.info("释放列表写锁");
            latch.countDown();
            log.info("信号量-1");
        }
    }


}
