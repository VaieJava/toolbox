package com.outdd.toolbox.reptile.novel.service;
import org.jsoup.nodes.Document;
import java.util.List;

/*
 * TODO: 爬行接口
 * @author VAIE
 * @date: 2018/11/12-9:28
 * @version v1.0
 */
public interface CrawlerService<T> {

    /**
     *TODO: 爬取详情信息
     * @param url 网络地址
     * @return NovelDetails
     */
    public T crawlInfo(String url);

    /**
     *TODO: 爬取详情信息
     * @param doc Document类
     * @return NovelDetails
     */
    public T crawlInfo(Document doc);

    /**
     *TODO: 爬取多个详情信息
     * @param url 网络地址
     * @return List<NovelDetails> 多个
     */
    public List<T> crawlInfos(String url,String parentId);

    /**
     *TODO: 爬取多个详情信息
     * @param doc Document类
     * @return List<NovelDetails> 多个
     */
    public List<T> crawlInfos(Document doc,String parentId);

}
