package com.outdd.toolbox.reptile.thread;

import com.outdd.toolbox.common.util.io.NovelIo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 处理资源数据线程
 */
public class ReadAccessThread implements Runnable {
    public ReadAccessThread(Queue<Document> queue, ReentrantReadWriteLock r, CountDownLatch latch) {
        this.queue = queue;
        this.r = r.readLock();
        this.latch = latch;
    }

    Lock r;
    CountDownLatch latch;
    Queue<Document> queue;
    NovelIo no = new NovelIo();
    Date data = new Date();

    @Override
    public void run() {
        read();
    }

    /**
     * TODO: 从队列中获取网页文件
     *
     * @param
     * @return: void
     * @auther: bjxdts
     * @date: 2018/10/31 15:13
     */
    public void read() {
        try {
            while (true) {

                System.out.println("读取开始");
                if (queue.size() > 0) {
                    r.lock();
                    System.out.println("成功获取锁");
                    no.write(getDetails(queue.poll()), data.getTime() + "");
                    r.unlock();
                } else {
//                    r.unlock();
//                    System.out.println("成功释放锁");
                    synchronized (queue) {
                        try {
                            System.out.println("读取线程等待");
                            queue.wait();//相当于queue.wait(0),队列中没有东西则默认无限等待直到队列中有东西并且通知他
                            System.out.println("读取线程启动");
                        } catch (InterruptedException e) {
                            System.out.println("什么错了");
                            e.printStackTrace();

                        }//如果队列中没有东西则等待
                    }
                }

                System.out.println("成功释放读取锁");
            }
        } catch (Exception e) {
            System.out.println("读取出错了");
        } finally {
            r.unlock();
            latch.countDown();
            System.out.println("-1");
        }

    }

    public String getDetails(Document doc){
        StringBuffer resultContent = new StringBuffer();
        Elements titles = doc.select(".j_chapterName");//标题
        Elements contents = doc.select(".j_readContent");//内容
        for (Element title : titles) {
            System.out.println("正在爬取" + title.text());
            resultContent.append(title.text() + "\r\n");
        }
        for (Element content : contents) {
            resultContent.append(content.text().replaceAll(" ", "\r\n") + "\r\n");
        }
        return resultContent.toString();
    }
}
