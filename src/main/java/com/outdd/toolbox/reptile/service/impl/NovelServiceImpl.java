package com.outdd.toolbox.reptile.service.impl;



import com.outdd.toolbox.reptile.pojo.NovelAssist;
import com.outdd.toolbox.reptile.service.NovelService;
import com.outdd.toolbox.reptile.thread.GetDirectoryToIoThread;
import com.outdd.toolbox.reptile.thread.GetDirectoryToQueueThread;
import com.outdd.toolbox.reptile.thread.GetNovelListThread;
import com.outdd.toolbox.reptile.util.ReptileUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class NovelServiceImpl implements NovelService {

    ExecutorService es =Executors.newCachedThreadPool();//线程池

    volatile Queue<Map<String, List<Document>>> queue = new LinkedList<Map<String, List<Document>>>();
    volatile Queue<List<Map<String,String>>> NovelList = new LinkedList<List<Map<String,String>>>();//
    String directoryRule = ".volume-wrap ul li a";
    String titleRule=".j_chapterName";
    String contentsRule=".j_readContent";

    @Override
    public void getNovelByAll(String url) {
        int xiet = 4;

        CountDownLatch latch = new CountDownLatch(xiet);//两个工人的协作
        ReentrantReadWriteLock listRwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock directoryRwl = new ReentrantReadWriteLock();


        NovelAssist novelAssist = new NovelAssist();
        novelAssist.setUrl(url);
        novelAssist.setLatch(latch);
        novelAssist.setDirectoryQueue(queue);
        novelAssist.setNovelList(NovelList);
        novelAssist.setListRwl(listRwl);
        novelAssist.setDirectoryRwl(directoryRwl);
        novelAssist.setListRule(".cf li .book-mid-info h4 a");
        novelAssist.setNextRule(".lbf-pagination-next");
        novelAssist.setDirectoryRule(directoryRule);
        novelAssist.setTitleRule(titleRule);
        novelAssist.setContentsRule(contentsRule);

        es.submit(new GetNovelListThread(novelAssist));
        es.submit(new GetDirectoryToQueueThread(novelAssist));
        es.submit(new GetDirectoryToQueueThread(novelAssist));
        es.submit(new GetDirectoryToIoThread(novelAssist));
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("结束了");
    }

    @Override
    public String getNovelByOne(String url,String title) {
        List<Document> fdList=ReptileUtil.getDirectoryAll(url,title,directoryRule);
        return ReptileUtil.DocumentToTxt(fdList,titleRule,contentsRule);
    }

    @Override
    public List<Map<String,String>> getNovelByName(String NovelName) {
        String url = "https://www.qidian.com/search?kw="+NovelName;
        String rule =".book-mid-info h4 a";
        String autoRule = ".book-mid-info .name";
        System.out.println("jhgjhfhjghddgf"+NovelName);
        return ReptileUtil.getNovelByUrlToList(url);
    }

}
