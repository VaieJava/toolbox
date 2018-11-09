package com.outdd.toolbox.reptile.novel.pojo;

import java.io.Serializable;

public class MergeDetailsVolume implements Serializable {
    private String detailsCode;

    private String volumeCode;

    private static final long serialVersionUID = 1L;

    public MergeDetailsVolume(String detailsCode, String volumeCode) {
        this.detailsCode = detailsCode;
        this.volumeCode = volumeCode;
    }

    public String getDetailsCode() {
        return detailsCode;
    }

    public String getVolumeCode() {
        return volumeCode;
    }
}