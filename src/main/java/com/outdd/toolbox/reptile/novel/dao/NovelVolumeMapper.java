package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;

import java.util.List;

public interface NovelVolumeMapper {
    int deleteByPrimaryKey(String code);

    int insert(NovelVolume record);

    int insertSelective(NovelVolume record);

    int insertBatch(List<NovelVolume> record);

    NovelVolume selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(NovelVolume record);

    int updateByPrimaryKey(NovelVolume record);
}