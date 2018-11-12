package com.outdd.toolbox.reptile.novel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    private Integer cateId;

    private Integer subCateId;

    private String cateNameCn;

    private String cateNameEn;

    private static final long serialVersionUID = 1L;


}