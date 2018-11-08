package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.NovelContent;

public interface NovelContentMapper {
    int deleteByPrimaryKey(String code);

    int insert(NovelContent record);

    int insertSelective(NovelContent record);

    NovelContent selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(NovelContent record);

    int updateByPrimaryKeyWithBLOBs(NovelContent record);
}