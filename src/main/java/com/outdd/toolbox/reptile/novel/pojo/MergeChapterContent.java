package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MergeChapterContent implements Serializable {
    private String chapterCode;

    private String contentCode;

    private static final long serialVersionUID = 1L;

    public MergeChapterContent(String chapterCode, String contentCode) {
        this.chapterCode = chapterCode;
        this.contentCode = contentCode;
    }

    public MergeChapterContent() {
    }
}