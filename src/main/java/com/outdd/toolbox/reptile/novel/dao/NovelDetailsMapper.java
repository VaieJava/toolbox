package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NovelDetailsMapper {
    int deleteByPrimaryKey(String code);

    int insert(NovelDetails record);

    int insertSelective(NovelDetails record);

    NovelDetails selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(NovelDetails record);

    int updateByPrimaryKeyWithBLOBs(NovelDetails record);

    int updateByPrimaryKey(NovelDetails record);
}