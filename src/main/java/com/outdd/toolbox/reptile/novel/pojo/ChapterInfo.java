package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ChapterInfo implements Serializable {
    private String uuid;

    private Long chapterId;

    private String chapterName;

    private Long next;

    private Long prev;

    private Date updateTime;

    private Long volumeId;

    private Long wordsCount;

    private Integer isfirst;

    private Integer chapterNum;

    private byte[] content;

    private static final long serialVersionUID = 1L;


}