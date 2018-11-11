package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;

import java.util.List;

public interface ChapterInfoMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(ChapterInfo record);

    int insertSelective(ChapterInfo record);

    int insertBatch(List<ChapterInfo> record);

    ChapterInfo selectByPrimaryKey(String uuid);

    List<ChapterInfo> selectByEntity(ChapterInfo record);

    int updateByPrimaryKeySelective(ChapterInfo record);

    int updateByPrimaryKeyWithBLOBs(ChapterInfo record);

    int updateByPrimaryKey(ChapterInfo record);
}