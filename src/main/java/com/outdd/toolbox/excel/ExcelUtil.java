package com.outdd.toolbox.excel;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.excel.service.ExcelXlsReader;
import com.outdd.toolbox.excel.service.ExcelXlsxReader;
import com.outdd.toolbox.excel.thread.ExWirtThread;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * TODO: excel工具类
 * @author VAIE
 * @date: 2018/9/30-14:53
 * @version v1.0
 */
@Service
public class ExcelUtil {
    //excel2003扩展名
    public static final String EXCEL03_EXTENSION = ".xls";
    //excel2007扩展名
    public static final String EXCEL07_EXTENSION = ".xlsx";
    /**
     * TODO: 读取excel表格
     * @param path 文件地址
     * @param objName 属性名
     * @return: List<JSONObject>
     * @auther: vaie
     * @date: 2018/9/27 22:02
     */
    public HSSFWorkbook readExcel(String path , String objName[]) throws IOException {

        List<JSONObject> list = new ArrayList<JSONObject>();
        //需要读取的xls文件
        FileInputStream file = new FileInputStream(path);
        Workbook work = new HSSFWorkbook(file);// 得到这个excel表格对象


//        list=readExcelToPt(work,objName);
//        list=readExcelToThread(work,objName);
        return null;
    }
    /**
     * 普通获取数据
     * @param work
     * @param objName
     * @return
     */
    public List<JSONObject> readExcelToPt(HSSFWorkbook work,String objName[]) {
        System.out.println("普通获取数据");
        List<JSONObject> list = new ArrayList<JSONObject>();

        for(int sheetIndex=0;sheetIndex<work.getNumberOfSheets();sheetIndex++){
            HSSFSheet sheet = work.getSheetAt(sheetIndex); //得到第一个sheet
            int rowNo = sheet.getLastRowNum(); //得到行数
            for (int i = 0; i <= rowNo; i++) {//循环行数
                HSSFRow row = sheet.getRow(i);//当前行
                int cellNo = row.getPhysicalNumberOfCells();//列数
                JSONObject jsobj=JSONObject.parseObject("{}");
                for (int j = 0; j < cellNo; j++) {//循环列数
                    String value=row.getCell((short) j).toString();
                    jsobj.put(objName[j],value);//添加数据
                }
                list.add(jsobj);//放入对象
            }

        }
        return list;
    }
    /**
     * 多线程获取数据
     * @param wk
     * @param objNamesz
     * @return
     */
    public List<JSONObject> readExcelToThread(HSSFWorkbook wk,String objNamesz[]) {
        System.out.println("多线程获取数据");

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        final HSSFWorkbook work=wk;
        final String objName[]=objNamesz;
        final List<JSONObject> listAll = new ArrayList<JSONObject>();
        final CountDownLatch end = new CountDownLatch(work.getNumberOfSheets());//线程通信计数器
        for(int sheetIndex=0;sheetIndex<work.getNumberOfSheets();sheetIndex++){
            final int shtt=sheetIndex;

            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    List<JSONObject> list = new ArrayList<JSONObject>();
                    HSSFSheet sheet = work.getSheetAt(shtt); //得到第一个sheet
                    int rowNo = sheet.getLastRowNum(); //得到行数
                    for (int i = 0; i <= rowNo; i++) {//循环行数
                        HSSFRow row = sheet.getRow(i);//当前行
                        int cellNo = row.getPhysicalNumberOfCells();//列数
                        JSONObject jsobj=JSONObject.parseObject("{}");
                        for (int j = 0; j < cellNo; j++) {//循环列数
                            String value=row.getCell((short) j).toString();
                            jsobj.put(objName[j],value);//添加数据
                        }
                        list.add(jsobj);//放入对象
                    }
                    listAll.addAll(list);
                    end.countDown();
                }
            });

        }
        try {
            end.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listAll;
    }

    /**
     * 每获取一条记录，即打印
     * 在flume里每获取一条记录即发送，而不必缓存起来，可以大大减少内存的消耗，这里主要是针对flume读取大数据量excel来说的
     * @param sheetName
     * @param sheetIndex
     * @param curRow
     * @param cellList
     */
    public static void sendRows(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList) {
        int i=0;
        StringBuffer oneLineSb = new StringBuffer();
        oneLineSb.append(filePath);
        oneLineSb.append("--");
        oneLineSb.append("sheet" + sheetIndex);
        oneLineSb.append("::" + sheetName);//加上sheet名
        oneLineSb.append("--");
        oneLineSb.append("row" + curRow);
        oneLineSb.append("::");
        for (String cell : cellList) {
            oneLineSb.append(cell.trim());
            oneLineSb.append("|");
            i++;
        }
        String oneLine = oneLineSb.toString();
        if (oneLine.endsWith("|")) {
            oneLine = oneLine.substring(0, oneLine.lastIndexOf("|"));
        }// 去除最后一个分隔符

        System.out.println(oneLine);
        System.out.println(i);
    }

    public static Map<String,List<JSONObject>> readExcel(File file) throws Exception {
        int totalRows =0;
        Map<String,List<JSONObject>> sheetMap = new HashMap<String,List<JSONObject>>();
        if (file.getPath().endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
            ExcelXlsReader excelXls=new ExcelXlsReader();
            totalRows =excelXls.process(file.getPath());
        } else if (file.getPath().endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
            ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader();
            sheetMap = excelXlsxReader.process(file);
        } else {
            throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
        }
        System.out.println("获取的集合大小"+sheetMap.size());

        return sheetMap;

    }

    /**
     * TODO: 获取sheet集合
     * @param wb
     * @return: java.util.List<org.apache.poi.hssf.usermodel.HSSFSheet>
     * @auther: vaie
     * @date: 2018/9/30 15:23
     */
    public List<HSSFSheet> getListSheet(HSSFWorkbook wb){
        String [] sheetName=new String[116];
        for(int i=0;i<sheetName.length;i++){
            sheetName[i]=(i+1)+"节";
        }
        List<HSSFSheet> listSheet=new ArrayList<HSSFSheet>();
        for(String s:sheetName){
            listSheet.add(wb.createSheet(s));
        }
        return listSheet;
    }
    public List<HSSFSheet> getListSheet(List<HSSFSheet>sheet,int index,int count){
        List<HSSFSheet> listSheet=new ArrayList<HSSFSheet>();
        int start=index*count;
        int end=(index+1)*count;
        for(int i=start;i<end;i++){
        try {
            listSheet.add(sheet.get(i));
        }catch (Exception e){
            continue;
        }

        }
        return listSheet;
    }

    /**
     * TODO: 写入excel
     * @param path
     * @return: void
     * @auther: vaie
     * @date: 2018/9/30 15:04
     */
    public void ExWrite(String path) throws Exception {
        int count=29;//单条线程处理条数
        int start=0;
        int end=6;
        HSSFWorkbook wb = new HSSFWorkbook(/*new FileInputStream(path)*/);
        List<HSSFSheet> listS=getListSheet(wb);
        int openThcount= (int) Math.ceil((double)listS.size()/count);//需要开启的线程数量
        ExecutorService es =Executors.newCachedThreadPool();//线程池

        CountDownLatch doneSignal = new CountDownLatch(openThcount);//使用计数栅栏
        final CyclicBarrier cb = new CyclicBarrier(openThcount);//号令枪

        FileOutputStream os=null;
        try {
            long startTime=System.currentTimeMillis();//记录开始时间

            //开启多线程
            for(int i=0;i<openThcount;i++){
                List<HSSFSheet> sheet =getListSheet(listS,i,count);
                es.submit(new ExWirtThread(doneSignal,sheet, start, end, cb));
            }
            doneSignal.await();//线程等待
            long endTime=System.currentTimeMillis();//记录结束时间
            float excTime=(float)(endTime-startTime)/1000;
            es.shutdown();//线程关闭
            System.out.println("共开启"+openThcount+"条线程------"+"创建数据执行了："+excTime+"s");
            os = new FileOutputStream(path);
            System.out.println("准备开始写入数据......");
            wb.write(os);
            os.flush();
            System.out.println("写入完成");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("______");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("*******");
        }finally {
            os.close();
            System.out.println("关闭流");
        }
    }

    /**
     * TODO: 进行封装
     * @param ins
    * @param file
     * @return: void
     * @auther: vaie
     * @date: 2018/10/27 15:55
     */

    public static void inputStreamToFile(InputStream ins,File file) {

        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
