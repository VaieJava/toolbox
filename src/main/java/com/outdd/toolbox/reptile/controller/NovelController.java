package com.outdd.toolbox.reptile.controller;

import com.outdd.toolbox.common.util.RegexUtils;
import com.outdd.toolbox.reptile.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/*
 * TODO: 小说控制器
 * @author VAIE
 * @date: 2018/11/3-14:00
 * @version v1.0
 */
@Controller
@RequestMapping("/novel/")
public class NovelController {
    @Autowired
    NovelService novelService;
    String site = "novel/";

    @RequestMapping("novelIndex")
    public String novelIndex(){
        return site+"novelRetileTool";
    }

    @ResponseBody
    @RequestMapping("getNovelByAll")
    public String getNovelByAll(String url){
        String info = "";
        if(RegexUtils.checkURL(url)){
            novelService.getNovelByAll(url);
            info = "正在执行中";
        }else {
            info = "url不对";
        }

        return info;
    }
//    @ResponseBody
    @RequestMapping("getNovelByOne")
    public void getNovelByOne(String url, String title, HttpServletResponse response){
        String info = "";
        String content="";
        if(RegexUtils.checkURL(url)){
            content=novelService.getNovelByOne(url,title);
        }else {
        }
        response.setContentType("application/force-download");// 设置强制下载不打开

        try {
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(title+".txt", "UTF-8"));// 设置文件名
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ServletOutputStream outputStream = null;
        BufferedOutputStream buffer = null;

        try {
            outputStream = response.getOutputStream();
            buffer = new BufferedOutputStream(outputStream);
            buffer.write(content.getBytes("UTF-8"));
            buffer.flush();
            buffer.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @ResponseBody
    @RequestMapping("getNovelByName")
    public List<Map<String,String>> getNovelByName(String name){
        return novelService.getNovelByName(name);
    }

}
