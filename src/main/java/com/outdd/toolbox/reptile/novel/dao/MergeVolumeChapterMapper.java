package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.MergeVolumeChapter;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MergeVolumeChapterMapper {
    int insert(MergeVolumeChapter record);

    int insertSelective(MergeVolumeChapter record);

    int insertBatch(List<MergeVolumeChapter> record);
}