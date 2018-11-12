package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.reptile.novel.service.CrawlerService;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.apache.poi.ss.formula.functions.T;
import org.jsoup.nodes.Document;

import java.util.List;

/*
 * TODO: 爬行接口-父级实现
 * @author VAIE
 * @date: 2018/11/12-9:36
 * @version v1.0
 */
public class ParentCrawlerServiceImpl<T> implements CrawlerService<T> {
    /**
     * TODO: 爬取详情信息
     *
     * @param url 网络地址
     * @return NovelDetails
     */
    @Override
    public T crawlInfo(String url) {
        return crawlInfo(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取详情信息
     *
     * @param doc Document类
     * @return NovelDetails
     */
    @Override
    public T crawlInfo(Document doc) {
        return null;
    }

    /**
     * TODO: 爬取多个详情信息
     *
     * @param url 网络地址
     * @return List<NovelDetails> 多个
     */
    @Override
    public List<T> crawlInfos(String url,String parentId) {
        return crawlInfos(ReptileUtil.getDocumentOfHttps(url),parentId);
    }

    /**
     * TODO: 爬取多个详情信息
     *
     * @param doc Document类
     * @return List<NovelDetails> 多个
     */
    @Override
    public List<T> crawlInfos(Document doc,String parentId) {
        return null;
    }
}
