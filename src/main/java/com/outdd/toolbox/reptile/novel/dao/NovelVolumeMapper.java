package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.MergeDetailsVolume;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NovelVolumeMapper {
    int deleteByPrimaryKey(String code);

    int insert(NovelVolume record);

    int insertSelective(NovelVolume record);

    int insertBatch(List<NovelVolume> record);

    NovelVolume selectByPrimaryKey(String code);

    List<NovelVolume> selectByPrimaryKeyList(List<MergeDetailsVolume> mdvList);

    int updateByPrimaryKeySelective(NovelVolume record);

    int updateByPrimaryKey(NovelVolume record);
}