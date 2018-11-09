package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NovelVolume implements Serializable {
    private String code;

    private String name;

    private Long chapteCnt;

    private Long wordCnt;

    private Integer toll;

    private static final long serialVersionUID = 1L;

    public NovelVolume(String code, String name, Long chapteCnt, Long wordCnt, Integer toll) {
        this.code = code;
        this.name = name;
        this.chapteCnt = chapteCnt;
        this.wordCnt = wordCnt;
        this.toll = toll;
    }

}