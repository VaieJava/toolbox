package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class NovelChapter implements Serializable {
    private String code;

    private String name;

    private Long wordCnt;

    private Integer toll;

    private Date premiereDate;

    private static final long serialVersionUID = 1L;


    private NovelContent novelContent;

    public NovelChapter(String code, String name, Long wordCnt, Integer toll, Date premiereDate) {
        this.code = code;
        this.name = name;
        this.wordCnt = wordCnt;
        this.toll = toll;
        this.premiereDate = premiereDate;
    }

    public NovelChapter() {
    }
}