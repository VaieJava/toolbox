package com.outdd.toolbox.common.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NovelIo {

    public void write(String details){
        FileWriter fw;
        File file = new File("D://1.txt");
        try{
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

                bw.write(details);

            bw.flush();
            bw.close();
        }
        catch(IOException e){
            // TODO Auto-generated catch blocke.
            e.printStackTrace();
        }
    }


}
