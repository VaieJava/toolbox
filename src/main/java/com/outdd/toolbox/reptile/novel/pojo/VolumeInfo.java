package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VolumeInfo implements Serializable {
    private String uuid;

    private Long volumeId;

    private String volumeName;

    private Integer volumeNum;

    private Long bookId;

    private Integer totalVolume;

    private Integer wordsCount;

    private List<ChapterInfo> chapterInfos;
    private static final long serialVersionUID = 1L;

}