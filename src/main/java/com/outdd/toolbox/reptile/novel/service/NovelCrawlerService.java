package com.outdd.toolbox.reptile.novel.service;

import com.outdd.toolbox.reptile.novel.pojo.NovelChapter;
import com.outdd.toolbox.reptile.novel.pojo.NovelContent;
import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import org.jsoup.nodes.Document;

import java.util.List;

/*
 * TODO: 爬行接口
 * @author VAIE
 * @date: 2018/11/9-22:37
 * @version v1.0
 */
public interface NovelCrawlerService {
    /**
     *TODO: 爬取小说详情信息
     * @param url 网络地址
     * @return NovelDetails
     */
    public NovelDetails crawlNovelDetails(String url);

    /**
     *TODO: 爬取小说详情信息
     * @param doc Document类
     * @return NovelDetails
     */
    public NovelDetails crawlNovelDetails(Document doc);

    /**
     *TODO: 爬取小说详情信息
     * @param url 网络地址
     * @return List<NovelDetails> 多个
     */
    public List<NovelDetails> crawlNovelDetailsList(String url);

    /**
     *TODO: 爬取小说详情信息
     * @param doc Document类
     * @return List<NovelDetails> 多个
     */
    public List<NovelDetails> crawlNovelDetailsList(Document doc);



    /**
     * TODO: 爬取小说卷信息
     * @param url 网络地址
     * @return: NovelVolume 卷类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public NovelVolume crawlNovelVolume(String url);

    /**
     * TODO: 爬取小说卷信息
     * @param doc Document类
     * @return: NovelVolume 卷类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public NovelVolume crawlNovelVolume(Document doc);

    /**
     *TODO: 爬取小说卷信息
     * @param url 网络地址
     * @return List<NovelVolume> 多个
     */
    public List<NovelVolume> crawlNovelVolumeList(String url);

    /**
     *TODO: 爬取小说卷信息
     * @param doc Document类
     * @return List<NovelVolume> 多个
     */
    public List<NovelVolume> crawlNovelVolumeList(Document doc);
    /**
     * TODO: 爬取小说章节信息
     * @param url 网络地址
     * @return: NovelVolume 章节类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public NovelChapter crawlNovelChapter(String url);

    /**
     * TODO: 爬取小说章节信息
     * @param doc Document类
     * @return: NovelVolume 章节类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public NovelChapter crawlNovelChapter(Document doc);

    /**
     *TODO: 爬取小说章节信息
     * @param url 网络地址
     * @return List<NovelChapter> 多个
     */
    public List<NovelChapter> crawlNovelChapterList(String url);

    /**
     *TODO: 爬取小说章节信息
     * @param doc Document类
     * @return List<NovelChapter> 多个
     */
    public List<NovelChapter> crawlNovelChapterList(Document doc);

    /**
     * TODO: 爬取小说内容信息
     * @param url 网络地址
     * @return: NovelVolume 内容类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public NovelContent crawlNovelContent(String url);

    /**
     * TODO: 爬取小说内容信息
     * @param doc Document类
     * @return: NovelVolume 内容类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public NovelContent crawlNovelContent(Document doc);


    /**
     *TODO: 爬取小说内容信息
     * @param url 网络地址
     * @return List<NovelChapter> 多个
     */
    public List<NovelContent> crawlNovelContentList(String url);

    /**
     *TODO: 爬取小说内容信息
     * @param doc Document类
     * @return List<NovelChapter> 多个
     */
    public List<NovelContent> crawlNovelContentList(Document doc);
}
