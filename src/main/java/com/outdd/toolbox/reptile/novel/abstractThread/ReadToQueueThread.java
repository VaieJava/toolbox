package com.outdd.toolbox.reptile.novel.abstractThread;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;

/*
 * TODO: 读取队列线程 (抽象类)
 * @author VAIE
 * @date: 2018/11/9-23:23
 * @version v1.0
 */
public abstract class ReadToQueueThread implements Runnable{

    public ReadToQueueThread(Queue<Object> queue, Lock readLock) {
        this.queue = queue;
        this.readLock = readLock;
    }

    Queue<Object> queue;//需要读取的队列
    Lock readLock; //读取锁
}
