package com.outdd.toolbox.reptile.novel.service;



import java.util.List;
import java.util.Map;

public interface NovelService {
    /**
     * 获取一个完整的url链接，获取小说进入硬盘
     * @param @param url
     * @param @return
     * @return
     * @throws
     */
    public void getNovelByAll(String url);

    /**
     * 获取一个完整的url链接，获取一本小说进入硬盘
     * @param @param url
     * @param @return
     * @return
     * @throws
     */
    public String getNovelByOne(String url,String title);

    /**
     * TODO: 根据小说获取列表
     * @param NovelName
     * @return: Object
     * @auther: vaie
     * @date: 2018/11/3 17:42
     */
    public List<Map<String,String>> getNovelByName(String NovelName);


}
