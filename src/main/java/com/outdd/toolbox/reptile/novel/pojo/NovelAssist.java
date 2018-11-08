package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * TODO: 小说辅助类
 * @author VAIE
 * @date: 2018/11/2-21:47
 * @version v1.0
 */
@Data
public class NovelAssist {
    String url;//网络地址
    Queue<Map<String,List<Document>>> directoryQueue;//节章标题队列
    Queue<List<Map<String,String>>> NovelList;//列表队列
    ReentrantReadWriteLock listRwl;//列表读写锁
    ReentrantReadWriteLock directoryRwl;//列表读写锁
    CountDownLatch latch;//信号量
    String titleRule;//标题规则
    String contentsRule;//内容规则
    String listRule;//列表规则
    String nextRule;//下一章规则
    String directoryRule;//目录的规则
}
