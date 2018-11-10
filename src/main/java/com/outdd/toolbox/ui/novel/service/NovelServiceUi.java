package com.outdd.toolbox.ui.novel.service;/*
 * TODO: 接口定义
 * @author VAIE
 * @date: 2018/11/9-15:52
 * @version v1.0
 */

import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;

import java.util.List;

public interface NovelServiceUi {
    public NovelDetails getNovelInfo(String id);

    public List<NovelVolume> getNovelVolume(String id);
}
