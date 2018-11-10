package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MergeVolumeChapter implements Serializable {
    private String volumeCode;

    private String chapterCode;

    private static final long serialVersionUID = 1L;

    public MergeVolumeChapter(String volumeCode, String chapterCode) {
        this.volumeCode = volumeCode;
        this.chapterCode = chapterCode;
    }

    public MergeVolumeChapter() {
    }
}