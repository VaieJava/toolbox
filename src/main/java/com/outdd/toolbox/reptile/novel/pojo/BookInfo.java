package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookInfo implements Serializable {
    private String uuid;

    private Long bookId;

    private String bookName;

    private String intro;

    private Integer cateId;

    private String author;

    private byte[] bookImg;

    private List<VolumeInfo> volumeInfos;

    private static final long serialVersionUID = 1L;

}