package com.outdd.toolbox.reptile.novel.thread;

import com.outdd.toolbox.common.util.io.NovelIo;
import com.outdd.toolbox.reptile.novel.pojo.NovelAssist;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * 获取目录向硬盘写入数据线程
 */
@Slf4j
public class GetDirectoryToIoThread implements Runnable {
    public GetDirectoryToIoThread(NovelAssist na) {
        this.directoryQueue = na.getDirectoryQueue();
        this.r = na.getDirectoryRwl().readLock();
        this.latch = na.getLatch();
        this.titleRule=na.getTitleRule();
        this.contentsRule=na.getContentsRule();
    }

    Lock r; //读取锁
    CountDownLatch latch; //信号量
    Queue<Map<String,List<Document>>> directoryQueue;//目录队列
    String titleRule;//标题规则
    String contentsRule;//内容规则
    NovelIo no = ReptileUtil.no;
    @Override
    public void run() {
        read(titleRule,contentsRule);
    }

    /**
     * TODO: 从队列中获取网页文件
     *
     * @param titleRule 标题规则
     * @param contentsRule 内容规则
     * @return: void
     * @auther: bjxdts
     * @date: 2018/10/31 15:13
     */
    public void read(String titleRule,String contentsRule) {
        log.info(Thread.currentThread().getName()+"开启");
        boolean filag = true;
        try {
            while (filag) {
                if (directoryQueue.size() > 0) {
                    r.lock();
                    Map<String,List<Document>> map = directoryQueue.poll();
                    r.unlock();
                    for (String fileName:map.keySet()){
                        String de=ReptileUtil.DocumentToTxt(map.get(fileName),titleRule,contentsRule);
                        no.write(de,fileName);
                    }

                } else {
                    synchronized (directoryQueue) {
                        while(directoryQueue.size() == 0) {
                            try {
                                log.info("目录队列空，等待数据");
                                Thread.sleep(30000);
                                log.info("唤醒队列检测");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            log.info("获取目录进硬盘执行完毕");
        } catch (Exception e) {
            log.error("读目录进硬盘出错");
        } finally {
            r.unlock();
            log.info("释放目录读锁");
            latch.countDown();
            log.info("信号量-1");
        }
    }


}
