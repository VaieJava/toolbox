package com.outdd.toolbox.ui.novel.controller;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.ui.novel.service.CategoryService;
import com.outdd.toolbox.ui.novel.service.NovelServiceUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
 * TODO: 小说前端显示
 * @author VAIE
 * @date: 2018/11/9-15:28
 * @version v1.0
 */
@Controller
@RequestMapping("/novelUi/")
public class NovelControllerUi {
    String site = "ui/novel/";

    @Autowired
    NovelServiceUi novelServiceUi;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("all")
    public String all(PageInfo<BookInfo> page,ModelMap mo){
        page.setPageSize(8);
        PageInfo<BookInfo> pageEntity = novelServiceUi.getBookInfo(page);
        String asd=CommomUtil.getPageDiv(pageEntity);
        mo.put("page",pageEntity);
        mo.put("pageDiv",asd);
        mo.put("categorys",categoryService.getFirstCategory(null));
        mo.put("title","全部作品");
        return site+"all";
    }
    /**
     * 返回小说信息页面
     * @return
     */
    @RequestMapping("info")
    public String info (String bookId, ModelMap mo){
        if(CommomUtil.isNotNull(bookId)){
            BookInfo bi=novelServiceUi.getBookInfo(bookId);
            mo.put("entity",bi);
            mo.put("title",bi.getBookName());
        }

        return site+"info";
    }
    /**
     * 返回小说信息页面
     * @return
     */
    @RequestMapping("chapter")
    public String chapter (String volumeId,String chapterId,ModelMap mo){
        if(CommomUtil.isNotNull(volumeId)&&CommomUtil.isNotNull(chapterId)){
            ChapterInfo ci=novelServiceUi.getChapter(volumeId,chapterId);
            Long bookId = novelServiceUi.getVolumeInfo(volumeId).getBookId();
            if(CommomUtil.isNotNull(bookId)){
                BookInfo bi=novelServiceUi.getBookInfo(String.valueOf(bookId));
                mo.put("book",bi);
            }
            mo.put("entity",ci);
            mo.put("title",ci.getChapterName());
        }
        return site+"chapter";
    }
}
