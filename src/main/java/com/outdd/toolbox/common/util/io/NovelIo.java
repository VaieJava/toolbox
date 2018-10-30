package com.outdd.toolbox.common.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class NovelIo {

    public void write(String details){
        String path = "D://"+new Date().getTime() +".txt";
        System.out.println("正在写入硬盘，请稍后、、、");
        FileWriter fw;
        File file = new File(path);
        try{
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

                bw.write(details);

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
