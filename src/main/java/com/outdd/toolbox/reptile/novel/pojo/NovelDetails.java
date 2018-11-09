package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NovelDetails implements Serializable {
    private String code;

    private String title;

    private String author;

    private String classify;

    private Integer over;

    private Integer signed;

    private Float score;

    private Long pageView;

    private Long wordNum;

    private String description;

    private List<NovelVolume> novelVolumes;

    private static final long serialVersionUID = 1L;


    public NovelDetails(String code, String title, String author, String classify, Integer over, Integer signed, Float score, Long pageView, Long wordNum, String description) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.classify = classify;
        this.over = over;
        this.signed = signed;
        this.score = score;
        this.pageView = pageView;
        this.wordNum = wordNum;
        this.description = description;
    }

    public NovelDetails() {
    }
}