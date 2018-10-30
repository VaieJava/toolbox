package com.outdd.toolbox.reptile.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Chapter implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;//小说章节
    private String url;//章节链接


}
