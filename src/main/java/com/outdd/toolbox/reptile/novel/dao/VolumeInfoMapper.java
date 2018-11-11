package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;

import java.util.List;

public interface VolumeInfoMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(VolumeInfo record);

    int insertSelective(VolumeInfo record);

    int insertBatch(List<VolumeInfo> record);

    VolumeInfo selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(VolumeInfo record);

    int updateByPrimaryKey(VolumeInfo record);
}