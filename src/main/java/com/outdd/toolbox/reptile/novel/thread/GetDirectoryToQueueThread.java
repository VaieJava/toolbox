package com.outdd.toolbox.reptile.novel.thread;

import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.pojo.NovelAssist;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import com.outdd.toolbox.reptile.novel.service.impl.NovelGroupServiceQiDianImpl;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/*
 * TODO: 获取目录线程
 * @author VAIE
 * @date: 2018/11/2-22:43
 * @version v1.0
 */
@Slf4j
public class GetDirectoryToQueueThread implements Runnable{

    public GetDirectoryToQueueThread(NovelAssist na,NovelGroupService novelGroupService){
        this.novelGroupService = novelGroupService;
        this.r = na.getListRwl().readLock();
        this.w = na.getDirectoryRwl().writeLock();
        this.directoryRule = na.getDirectoryRule();
        this.directoryQueue = na.getDirectoryQueue();
        this.NovelList = na.getNovelList();
        this.latch = na.getLatch();
    }
    String directoryRule;
    volatile  Queue<List<Map<String,String>>> NovelList;
    Queue<Map<String,List<Document>>> directoryQueue;
    Lock w;//写锁
    Lock r;//读锁
    CountDownLatch latch;//信号量
    NovelGroupService novelGroupService;

    @Override
    public void run() {
        test();
    }

    public void test(){
        log.info(Thread.currentThread().getName()+"目录开启");
        boolean filag = true;

        try {
            while (filag) {
                if (NovelList.size() > 0) {
                    List<Map<String,String>> novelList=null;
                    synchronized (r){
                    r.lock();//获取局部读锁
                        novelList=NovelList.poll();
                    System.out.println(NovelList.size());
                    r.unlock();
                    }

                    for(Map<String,String> map:novelList){
                        String url=map.get("url");
                        boolean i=novelGroupService.insetNovel(url);
                        if(i){
                            log.info("成功");
                        }
                    }
                } else {
                    synchronized (NovelList) {
                        while(NovelList.size() == 0) {
                            try {
                                log.info("列表队列空，等待数据");
                                Thread.sleep(10000);
                                log.info("唤醒队列检测");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            log.info("读取目录执行完毕");
        }catch (Exception e){
            log.error("读目录出错");
            e.printStackTrace();
        }finally {
            r.unlock();//释放局部读锁
            log.info("释放目录读锁");
            latch.countDown();
            log.info("信号量-1");
        }
    }
    /**
     * TODO: 获取全部目录
     * @param directoryRule 目录的规则
     * @return: void
     * @auther: vaie
     * @date: 2018/11/1 21:12
     */
    public void getNovelDirectory(String directoryRule){
        log.info(Thread.currentThread().getName()+"目录开启");
        boolean filag = true;

            try {
                while (filag) {
                    if (NovelList.size() > 0) {
                        r.lock();//获取局部读锁
                        List<Map<String,String>> novelList=NovelList.poll();
                        r.unlock();
                        for(Map<String,String> map:novelList){
                            String url=map.get("url");
                            String title=map.get("title");
                            List<Document> fdList=ReptileUtil.getDirectoryAll(url,title,directoryRule);
                            if(CommomUtil.isNotNull(fdList)){
                                Map<String,List<Document>> fdmap = new HashMap<String,List<Document>>();
                                fdmap.put(title,fdList);
                                w.lock();
                                directoryQueue.offer(fdmap);
                                w.unlock();
                            }
                        }
                    } else {
                        synchronized (NovelList) {
                            while(NovelList.size() == 0) {
                                try {
                                    log.info("列表队列空，等待数据");
                                    Thread.sleep(10000);
                                    log.info("唤醒队列检测");
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                log.info("读取目录执行完毕");
            }catch (Exception e){
                log.error("读目录出错");
                e.printStackTrace();
            }finally {
                r.unlock();//释放局部读锁
                log.info("释放目录读锁");
                latch.countDown();
                log.info("信号量-1");
            }

    }


}
