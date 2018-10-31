package com.outdd.toolbox.common.util.io;

import java.io.*;
import java.util.Date;

public class NovelIo {

    public void write(String details,String fileName){
        String path = "D://"+fileName +".txt";
        System.out.println("正在写入硬盘，请稍后、、、");
        FileWriter fw;
        File file = new File(path);
        try{
            fw = new FileWriter(file,true);
            PrintWriter bw = new PrintWriter(fw);

                bw.println(details);

            bw.flush();
            bw.close();
            System.out.println("写人成功"+path);
        }
        catch(IOException e){
            // TODO Auto-generated catch blocke.
            System.out.println("写人失败");
            e.printStackTrace();
        }
    }


}
