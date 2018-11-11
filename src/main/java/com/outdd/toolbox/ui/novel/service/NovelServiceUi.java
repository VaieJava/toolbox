package com.outdd.toolbox.ui.novel.service;/*
 * TODO: 接口定义
 * @author VAIE
 * @date: 2018/11/9-15:52
 * @version v1.0
 */

import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;

import java.util.List;

public interface NovelServiceUi {
    public BookInfo getBookInfo(String id);

    public List<BookInfo> getBookInfo(BookInfo book);

    public VolumeInfo getVolumeInfo(String bookId);

    public ChapterInfo getChapter(String volumeId,String chapterId);
}
