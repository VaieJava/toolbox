package com.outdd.toolbox.excel;

/*
 * TODO: excel测试入口方法
 * @author VAIE
 * @date: 2018/9/30-14:53
 * @version v1.0
 */
public class ExMain {
    static String  path="C:\\Users\\Administrator\\Desktop\\data(1).xlsx";
    public static void main(String[] args) throws Exception {
        String read_write="write";

        //查看内存量
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory(); // 最大内存
        long totalMemory = runtime.totalMemory(); // 默认使用总内存
        System.out.println("最大内存数："+maxMemory+"字节、"+(maxMemory/(double)1024/1024)+"MB");
        System.out.println("默认使用总内存："+totalMemory+"字节、"+(totalMemory/(double)1024/1024)+"MB");

        //统计方法耗时时间
        long startTime=0;//记录开始时间
        long endTime=0;//记录结束时间
        float excTime=0;
        //读
        if("read".equals(read_write)){
            try {
                startTime=System.currentTimeMillis();//记录开始时间
                ExcelUtil ex=new ExcelUtil();
//                ex.readExcel(path);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("读取异常");
            }finally {
                endTime=System.currentTimeMillis();//记录结束时间
                excTime=(float)(endTime-startTime)/1000;
                System.out.println("读取执行时间："+excTime+"s");
            }
        }
        //写
        else if("write".equals(read_write)){

            try {
                startTime=System.currentTimeMillis();//记录开始时间
                ExcelUtil ex=new ExcelUtil();
            ex.ExWrite(path);

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("写入异常");
            }finally {
                endTime=System.currentTimeMillis();//记录结束时间
                excTime=(float)(endTime-startTime)/1000;
                System.out.println("写入执行时间："+excTime+"s");
            }
        }
    }
}
