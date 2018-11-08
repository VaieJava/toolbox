package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NovelDetails implements Serializable {
    private String code;

    private String title;

    private String author;

    private String classify;

    private Integer over;

    private Integer signed;

    private Float score;

    private Long pageView;

    private Long wordNum;

    private String description;

    private List<NovelVolume> novelVolumesList;

    private static final long serialVersionUID = 1L;

}