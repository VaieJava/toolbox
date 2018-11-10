package com.outdd.toolbox.ui.novel.controller;/*
 * TODO: 小说前端显示
 * @author VAIE
 * @date: 2018/11/9-15:28
 * @version v1.0
 */

import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import com.outdd.toolbox.ui.novel.service.NovelServiceUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/novelUi/")
public class NovelControllerUi {
    String site = "ui/novel/";

    @Autowired
    NovelServiceUi novelServiceUi;

    /**
     * 返回小说信息页面
     * @return
     */
    @RequestMapping("info")
    public String info (ModelMap mo){

        NovelDetails nd=novelServiceUi.getNovelInfo("ee1f56cafc064ff89a1efe58ff753379");
        List<NovelVolume> nvList=novelServiceUi.getNovelVolume(nd.getCode());
        mo.put("title",nd.getTitle()+"_"+nd.getAuthor()+"_"+nd.getClassify());

        mo.put("entity",nd);
        mo.put("volumeList",nvList);
        return site+"info";
    }
}
