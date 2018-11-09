package com.outdd.toolbox.reptile.novel.pojo;

import java.io.Serializable;

public class MergeVolumeChapter implements Serializable {
    private String volumeCode;

    private String chapterCode;

    private static final long serialVersionUID = 1L;

    public MergeVolumeChapter(String volumeCode, String chapterCode) {
        this.volumeCode = volumeCode;
        this.chapterCode = chapterCode;
    }

    public String getVolumeCode() {
        return volumeCode;
    }

    public String getChapterCode() {
        return chapterCode;
    }
}