package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NovelChapter implements Serializable {
    private String code;

    private String name;

    private Long wordCnt;

    private Integer toll;

    private Date premiereDate;

    private static final long serialVersionUID = 1L;

    private String detailsCode;


}