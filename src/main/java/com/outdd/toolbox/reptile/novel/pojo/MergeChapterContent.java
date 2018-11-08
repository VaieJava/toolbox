package com.outdd.toolbox.reptile.novel.pojo;

import java.io.Serializable;

public class MergeChapterContent implements Serializable {
    private String chapterCode;

    private String contentCode;

    private static final long serialVersionUID = 1L;

    public MergeChapterContent(String chapterCode, String contentCode) {
        this.chapterCode = chapterCode;
        this.contentCode = contentCode;
    }

    public String getChapterCode() {
        return chapterCode;
    }

    public String getContentCode() {
        return contentCode;
    }
}