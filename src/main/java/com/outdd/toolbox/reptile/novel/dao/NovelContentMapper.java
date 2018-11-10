package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.NovelContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NovelContentMapper {
    int deleteByPrimaryKey(String code);

    int insert(NovelContent record);

    int insertSelective(NovelContent record);

    NovelContent selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(NovelContent record);

    int updateByPrimaryKeyWithBLOBs(NovelContent record);
}