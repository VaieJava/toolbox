package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NovelVolume implements Serializable {
    private String code;

    private String name;

    private long chapteCnt;

    private long wordCnt;

    private Integer toll;

    private List<NovelChapter> novelChapterList;

    private static final long serialVersionUID = 1L;

}