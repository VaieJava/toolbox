package com.outdd.toolbox.reptile.service;

import com.outdd.toolbox.reptile.pojo.Chapter;

import java.util.List;

public interface IChapterInter {
    /**
     * 获取一个完整的url链接，显示所有章节列表
     * @param @param url
     * @param @return
     * @return
     * @throws
     */
    public List<Chapter> getChapter(String url);
}
