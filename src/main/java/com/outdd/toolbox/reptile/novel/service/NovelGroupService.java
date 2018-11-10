package com.outdd.toolbox.reptile.novel.service;

/*
 * TODO: 小说组合入库逻辑层接口
 * @author VAIE
 * @date: 2018/11/9-23:38
 * @version v1.0
 */
public interface NovelGroupService {
    /**
     * TODO:新增一本小说
     * @param url 网络地址
     * @return
     */
    public boolean insetNovel(String url);
}
