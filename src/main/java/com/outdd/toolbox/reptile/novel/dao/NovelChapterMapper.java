package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.NovelChapter;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NovelChapterMapper {
    int deleteByPrimaryKey(String code);

    int insert(NovelChapter record);

    int insertSelective(NovelChapter record);

    int insertBatch(List<NovelChapter> record);

    NovelChapter selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(NovelChapter record);

    int updateByPrimaryKey(NovelChapter record);
}