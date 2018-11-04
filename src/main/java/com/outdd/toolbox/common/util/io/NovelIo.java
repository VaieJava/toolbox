package com.outdd.toolbox.common.util.io;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

public class NovelIo {

    public void write(String details,String fileName){
        String path = "D://"+new Date().getTime()+"/xiaoshuo/"+fileName +".txt";
        FileWriter fw;
        File file = new File(path);
        //获取父目录
        File fileParent = file.getParentFile();
        //判断是否存在
        if (!fileParent.exists()) {
            //创建父目录文件
            fileParent.mkdirs();
        }
        try{
            fw = new FileWriter(file,true);
            PrintWriter bw = new PrintWriter(fw);
                bw.println(details);
            bw.flush();
            bw.close();
        }
        catch(IOException e){
            // TODO Auto-generated catch blocke.
            System.out.println("写人失败");
            e.printStackTrace();
        }
    }


    public void downloadNovle(HttpServletResponse response,File file){

        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


}
