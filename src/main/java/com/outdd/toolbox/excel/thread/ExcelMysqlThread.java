package com.outdd.toolbox.excel.thread;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.excel.service.ExcelService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/*
 * TODO: 添加数据
 * @author VAIE
 * @date: 2018/10/18-20:07
 * @version v1.0
 */
@Slf4j
public class ExcelMysqlThread implements Runnable {

    private final CountDownLatch doneSignal;//信号量
    private CyclicBarrier barrier;//信号枪
    private ExcelService excelService;;//逻辑层实现
    private List<Map.Entry<String,List<JSONObject>>> list;//数据集合
    /**
     * TODO: 构造方法
     * @param doneSignal//信号量
     * @return:
     * @auther: Yang
     * @date: 2018-10-18
     */
    public ExcelMysqlThread(CountDownLatch doneSignal,CyclicBarrier barrier,ExcelService excelService,List<Map.Entry<String,List<JSONObject>>> list) {
        this.barrier = barrier;
        this.doneSignal = doneSignal;
        this.excelService = excelService;
        this.list = list;
    }



    @Override
    public void run() {
        try {
            log.info(Thread.currentThread().getName() + " 准备就绪");
            barrier.await();
            log.info(Thread.currentThread().getName() + " 起跑");
            for(Map.Entry<String,List<JSONObject>> entry : list){
                String fields="";
                for (Map.Entry<String, Object> entryz : entry.getValue().get(0).entrySet()) {
                    fields+=" "+entryz.getKey()+" varchar(255),";
                }
                fields=fields.substring(0,fields.length()-1);
                excelService.createNewTable(entry.getKey(),fields);
                excelService.insert(entry.getKey(),entry.getValue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            doneSignal.countDown();
            log.info(Thread.currentThread().getName()+" Count: " + doneSignal.getCount());
        }
    }
}
