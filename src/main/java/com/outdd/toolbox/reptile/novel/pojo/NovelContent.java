package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
@Data
public class NovelContent implements Serializable {
    private String code;

    private byte[] content;

    private static final long serialVersionUID = 1L;

    public NovelContent(String code, byte[] content) {
        this.code = code;
        this.content = content;
    }

    public NovelContent() {
    }
}