package com.outdd.toolbox.excel.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/*
 * TODO: excel写入线程
 * @author VAIE
 * @date: 2018/9/30-14:53
 * @version v1.0
 */
@Slf4j
public class ExWirtThread implements Runnable{

    /**
     * TODO: 构造方法
     * @param doneSignal//信号量
     * @param listSheet //sheet
     * @param start //开始条数
     * @param end //结束条数
     * @param barrier //信号枪
     * @return:
     * @auther:
     * @date:
     */
    public ExWirtThread(CountDownLatch doneSignal, List<HSSFSheet> listSheet, int start,
                        int end,CyclicBarrier barrier) {

        this.doneSignal = doneSignal;
        this.listSheet = listSheet;
        this.start = start;
        this.end = end;
        this.barrier = barrier;
    }

    private final CountDownLatch doneSignal;//信号量
    private List<HSSFSheet> listSheet;//sheet
    private int start;//开始条数
    private int end;//结束条数
    private CyclicBarrier barrier;//信号枪
    Object oj= new Object();
    String []  titleRow={"ordinal","age","name","sex","number"};//头

    /**
     * TODO: 获取随机中文字符串
     * @param
     * @return: java.lang.String
     * @auther: vaie
     * @date: 2018/9/30 15:14
     */
    public static synchronized String getStringToChar() {
        char ca[]=new char[3];
        for(int z=0; z<3;z++){
            ca[z]=(char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
        }
        return new String(ca);
    }
    @Override
    public void run() {

        try {
            log.info(Thread.currentThread().getName() + " 准备就绪");
            barrier.await();
            log.info(Thread.currentThread().getName() + " 起跑");

            for(HSSFSheet sheet:listSheet){
                String aString=getStringToChar();
                int i = start;
                while (i < end) {
                    //添加表头
                    HSSFRow row = sheet.createRow(i);    //创建第一行
                    for (int j = 0; j < titleRow.length-1; j++) {


                        HSSFCell cell = row.getCell(j);
                        if (cell == null) {
                            cell = row.createCell(j);
                        }
                        if(j==0){
                            cell.setCellValue(i);
                        }if (i == 0) {
                            cell.setCellValue(titleRow[j]);
                        }else{
                            cell.setCellValue(aString);
                        }
                    }
                    ++i;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            doneSignal.countDown();
            log.info(Thread.currentThread().getName()+",start: " + start + " end: " + end						+ " Count: " + doneSignal.getCount());			}

    }
}
