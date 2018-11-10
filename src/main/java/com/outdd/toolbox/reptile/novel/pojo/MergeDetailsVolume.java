package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MergeDetailsVolume implements Serializable {
    private String detailsCode;

    private String volumeCode;

    private static final long serialVersionUID = 1L;

    public MergeDetailsVolume(String detailsCode, String volumeCode) {
        this.detailsCode = detailsCode;
        this.volumeCode = volumeCode;
    }

    public MergeDetailsVolume() {
    }
}