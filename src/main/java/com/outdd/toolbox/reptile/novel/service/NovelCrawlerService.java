package com.outdd.toolbox.reptile.novel.service;

import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
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
    public BookInfo crawlNovelDetails(String url);

    /**
     *TODO: 爬取小说详情信息
     * @param doc Document类
     * @return NovelDetails
     */
    public BookInfo crawlNovelDetails(Document doc);

    /**
     *TODO: 爬取小说详情信息
     * @param url 网络地址
     * @return List<NovelDetails> 多个
     */
    public List<BookInfo> crawlNovelDetailsList(String url);

    /**
     *TODO: 爬取小说详情信息
     * @param doc Document类
     * @return List<NovelDetails> 多个
     */
    public List<BookInfo> crawlNovelDetailsList(Document doc);



    /**
     * TODO: 爬取小说卷信息
     * @param url 网络地址
     * @return: NovelVolume 卷类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public VolumeInfo crawlNovelVolume(String url);

    /**
     * TODO: 爬取小说卷信息
     * @param doc Document类
     * @return: NovelVolume 卷类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public VolumeInfo crawlNovelVolume(Document doc);

    /**
     *TODO: 爬取小说卷信息
     * @param url 网络地址
     * @return List<NovelVolume> 多个
     */
    public List<VolumeInfo> crawlNovelVolumeList(String url);

    /**
     *TODO: 爬取小说卷信息
     * @param doc Document类
     * @return List<NovelVolume> 多个
     */
    public List<VolumeInfo> crawlNovelVolumeList(Document doc);
    /**
     * TODO: 爬取小说章节信息
     * @param url 网络地址
     * @return: NovelVolume 章节类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public ChapterInfo crawlNovelChapter(String url);

    /**
     * TODO: 爬取小说章节信息
     * @param doc Document类
     * @return: NovelVolume 章节类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    public ChapterInfo crawlNovelChapter(Document doc);

    /**
     *TODO: 爬取小说章节信息
     * @param url 网络地址
     * @return List<NovelChapter> 多个
     */
    public List<ChapterInfo> crawlNovelChapterList(String url);

    /**
     *TODO: 爬取小说章节信息
     * @param doc Document类
     * @return List<NovelChapter> 多个
     */
    public List<ChapterInfo> crawlNovelChapterList(Document doc);

}
