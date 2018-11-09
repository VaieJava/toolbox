package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.MergeChapterContent;

public interface MergeChapterContentMapper {
    int insert(MergeChapterContent record);

    int insertSelective(MergeChapterContent record);
}