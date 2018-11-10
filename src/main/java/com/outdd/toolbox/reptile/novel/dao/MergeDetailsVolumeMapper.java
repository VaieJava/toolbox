package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.MergeDetailsVolume;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MergeDetailsVolumeMapper {
    int insert(MergeDetailsVolume record);

    int insertSelective(MergeDetailsVolume record);

    int insertBatch(List<MergeDetailsVolume> record);

    List<MergeDetailsVolume> selectByPrimaryKey(String code);
}