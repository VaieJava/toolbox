package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.pojo.NovelChapter;
import com.outdd.toolbox.reptile.novel.pojo.NovelContent;
import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import com.outdd.toolbox.reptile.novel.service.NovelCrawlerService;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * TODO: 爬行实现（起点网站实现方式）
 * @author VAIE
 * @date: 2018/11/9-22:44
 * @version v1.0
 */
@Service
public class NovelCrawlerServiceQiDianImpl implements NovelCrawlerService {
    /**
     * TODO: 爬取小说详情信息
     *
     * @param url 网络地址
     * @return NovelDetails
     */
    @Override
    public NovelDetails crawlNovelDetails(String url) {

        return crawlNovelDetails(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取小说卷信息
     *
     * @param url 网络地址
     * @return: NovelVolume 卷类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    @Override
    public NovelVolume crawlNovelVolume(String url) {
        return crawlNovelVolume(ReptileUtil.getDocumentOfHttps(url));
    }


    /**
     * TODO: 爬取小说章节信息
     *
     * @param url 网络地址
     * @return: NovelVolume 章节类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    @Override
    public NovelChapter crawlNovelChapter(String url) {
        return crawlNovelChapter(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取小说内容信息
     *
     * @param url 网络地址
     * @return: NovelVolume 内容类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    @Override
    public NovelContent crawlNovelContent(String url) {
        return crawlNovelContent(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取小说详情信息
     *
     * @param url 网络地址
     * @return List<NovelDetails> 多个
     */
    @Override
    public List<NovelDetails> crawlNovelDetailsList(String url) {
        return crawlNovelDetailsList(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取小说卷信息
     *
     * @param url 网络地址
     * @return List<NovelVolume> 多个
     */
    @Override
    public List<NovelVolume> crawlNovelVolumeList(String url) {
        return crawlNovelVolumeList(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取小说章节信息
     *
     * @param url 网络地址
     * @return List<NovelChapter> 多个
     */
    @Override
    public List<NovelChapter> crawlNovelChapterList(String url) {
        return crawlNovelChapterList(ReptileUtil.getDocumentOfHttps(url));
    }


    /**
     * TODO: 爬取小说内容信息
     *
     * @param url 网络地址
     * @return List<NovelChapter> 多个
     */
    @Override
    public List<NovelContent> crawlNovelContentList(String url) {
        return crawlNovelContentList(ReptileUtil.getDocumentOfHttps(url));
    }

    //*****************************************************************************************************
    //*                                                                                                   *
    //*                                                                                                   *
    //*                                                                                                   *
    //*                                                                                                   *
    //*****************************************************************************************************


    /**
     * TODO: 爬取小说详情信息
     *
     * @param doc Document类
     * @return NovelDetails
     */
    @Override
    public NovelDetails crawlNovelDetails(Document doc) {
        NovelDetails entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new NovelDetails();
            Elements titleUrls = doc.select(".book-info");
            for (Element titleUrl : titleUrls) {
                //标题
                entity.setTitle(titleUrl.child(0).child(0).text());
                //作者
                entity.setAuthor(titleUrl.child(0).child(1).child(0).text());
                //分类
                entity.setClassify(titleUrl.child(1).child(titleUrl.siblingIndex()-1).text()+""+titleUrl.child(1).child(titleUrl.siblingIndex()).text());
                //简介
                entity.setDescription(doc.select(".book-intro").text().replaceAll(" ", "\r\n") + "\r\n");
                //作品图片
                //作品信息
                //是否完结
                entity.setOver(0);
                //是否签约
                entity.setSigned(0);
                //是否收费

                //评分
                entity.setScore((float) 0.1);
                //识别码
                entity.setCode(CommomUtil.uuid());
                //获取卷
                entity.setNovelVolumes(crawlNovelVolumeList(doc));
            }

        }
        return entity;
    }



    /**
     * TODO: 爬取小说详情信息
     *
     * @param doc Document类
     * @return List<NovelDetails> 多个
     */
    @Override
    public List<NovelDetails> crawlNovelDetailsList(Document doc) {
        List<NovelDetails> list = null;
        if (CommomUtil.isNotNull(doc)) {
                list = new ArrayList<NovelDetails>();
                for (Element e : doc.select("")) {
                    list.add(crawlNovelDetails(Jsoup.parse(e.toString())));
                }
        }
        return list;
    }

    /**
     * TODO: 爬取小说卷信息
     *
     * @param doc Document类
     * @return: NovelVolume 卷类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    @Override
    public NovelVolume crawlNovelVolume(Document doc) {
        NovelVolume entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new NovelVolume();
            Elements volumes = doc.select("h3");
            for (Element volume : volumes) {
                String volumeName=volume.text().split("·")[0].split(" ")[volume.text().split("·")[0].split(" ").length-1];
                if(volumeName!=null && volumeName.trim().length()!=0){
                    //第几卷
                    //卷名称
                    entity.setName(volumeName);
                    //是否收费
                    entity.setToll(0);
                    //卷字数
//                    entity.setWordCnt(0);
                    //章数
//                    entity.setChapteCnt(0);
                    //识别码
                    entity.setCode(CommomUtil.uuid());
                    //获取章节
                    entity.setNovelChapterList(crawlNovelChapterList(doc));
                }
            }
        }
        return entity;
    }



    /**
     * TODO: 爬取小说卷信息
     *
     * @param doc Document类
     * @return List<NovelVolume> 多个
     */
    @Override
    public List<NovelVolume> crawlNovelVolumeList(Document doc) {
        List<NovelVolume> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<NovelVolume>();
            for (Element e : doc.select(".volume")) {
                list.add(crawlNovelVolume(Jsoup.parse(e.toString())));
            }
        }
        return list;
    }


    /**
     * TODO: 爬取小说章节信息
     *
     * @param doc Document类
     * @return: NovelVolume 章节类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    @Override
    public NovelChapter crawlNovelChapter(Document doc) {
        NovelChapter entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new NovelChapter();
                //第几章
                //章节名称
//                entity.setName(doc.text());
                //章节url
                String url= ReptileUtil.getUrl(doc);
                //章节首发时间
                entity.setPremiereDate(new Date());
                //章节是否收费
                entity.setToll(0);
                //章节字数
                entity.setWordCnt((long) 0);
                //识别码
                entity.setCode(CommomUtil.uuid());
                //获取章节里的内容
                entity.setNovelContent(crawlNovelContent(url));
        }
        return entity;
    }



    /**
     * TODO: 爬取小说章节信息
     *
     * @param doc Document类
     * @return List<NovelChapter> 多个
     */
    @Override
    public List<NovelChapter> crawlNovelChapterList(Document doc) {
        List<NovelChapter> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<NovelChapter>();
            for (Element e : doc.select(".cf li a")) {
                list.add(crawlNovelChapter(Jsoup.parse(e.toString())));
            }
        }
        return list;
    }


    /**
     * TODO: 爬取小说内容信息
     *
     * @param doc Document类
     * @return: NovelVolume 内容类
     * @auther: vaie
     * @date: 2018/11/9 22:40
     */
    @Override
    public NovelContent crawlNovelContent(Document doc) {
        NovelContent entity = null;
        if (CommomUtil.isNotNull(doc)) {
            Element content = doc.select(".j_readContent").get(0);//内容
            entity = new NovelContent();
            //第几章

            //具体内容
            entity.setContent((content.text().replaceAll(" ", "\r\n") + "\r\n").getBytes());
            //上一章url
            //下一章url
            //识别码
            entity.setCode(CommomUtil.uuid());
        }
        return entity;
    }


    /**
     * TODO: 爬取小说内容信息
     *
     * @param doc Document类
     * @return List<NovelChapter> 多个
     */
    @Override
    public List<NovelContent> crawlNovelContentList(Document doc) {
        List<NovelContent> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<NovelContent>();
            for (Element e : doc.select("")) {
                list.add(crawlNovelContent(Jsoup.parse(e.toString())));
            }
        }
        return list;
    }
}
