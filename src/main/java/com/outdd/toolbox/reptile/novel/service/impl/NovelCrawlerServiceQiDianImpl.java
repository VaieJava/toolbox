package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.NovelCrawlerService;
import com.outdd.toolbox.reptile.novel.util.NovelUtil;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

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
    public BookInfo crawlNovelDetails(String url) {

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
    public VolumeInfo crawlNovelVolume(String url) {
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
    public ChapterInfo crawlNovelChapter(String url) {
        return crawlNovelChapter(ReptileUtil.getDocumentOfHttps(url));
    }


    /**
     * TODO: 爬取小说详情信息
     *
     * @param url 网络地址
     * @return List<NovelDetails> 多个
     */
    @Override
    public List<BookInfo> crawlNovelDetailsList(String url) {
        return crawlNovelDetailsList(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取小说卷信息
     *
     * @param url 网络地址
     * @return List<NovelVolume> 多个
     */
    @Override
    public List<VolumeInfo> crawlNovelVolumeList(String url) {
        return crawlNovelVolumeList(ReptileUtil.getDocumentOfHttps(url));
    }

    /**
     * TODO: 爬取小说章节信息
     *
     * @param url 网络地址
     * @return List<NovelChapter> 多个
     */
    @Override
    public List<ChapterInfo> crawlNovelChapterList(String url) {
        return crawlNovelChapterList(ReptileUtil.getDocumentOfHttps(url));
    }


    //*****************************************************************************************************
    //*                                                                                                   *
    //*                                                                                                   *
    //*                                                                                                   *
    //*                                                                                                   *
    //*****************************************************************************************************

    private long bookId;
    private long volumeId;
    /**
     * TODO: 爬取小说详情信息
     *
     * @param doc Document类
     * @return NovelDetails
     */
    @Override
    public BookInfo crawlNovelDetails(Document doc) {

        BookInfo entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new BookInfo();
            try {
                Elements imgs = doc.select(".book-img img");
                String  src=imgs.get(0).attr("src");
                //作品图片
                entity.setBookImg(src.getBytes());
            }catch (Exception e){

            }

            Elements titleUrls = doc.select(".book-info");
            for (Element titleUrl : titleUrls) {
                //标题
                entity.setBookName(titleUrl.child(0).child(0).text());
                //作者
                entity.setAuthor(titleUrl.child(0).child(1).child(0).text());
                //分类
//                entity.setCateId(titleUrl.child(1).child(titleUrl.siblingIndex()-1).text()+""+titleUrl.child(1).child(titleUrl.siblingIndex()).text());
                //简介
                entity.setIntro(doc.select(".book-intro").text().replaceAll(" ", "\r\n") + "\r\n");

                //作品信息
                bookId = CommomUtil.numId();
                entity.setBookId(bookId);
                //是否完结
                //是否签约
                //是否收费
                //评分
                //识别码
                entity.setUuid(CommomUtil.uuid());
                //获取卷
                entity.setVolumeInfos(crawlNovelVolumeList(doc));
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
    public List<BookInfo> crawlNovelDetailsList(Document doc) {
        List<BookInfo> list = null;
        if (CommomUtil.isNotNull(doc)) {
                list = new ArrayList<BookInfo>();
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
    public VolumeInfo crawlNovelVolume(Document doc) {
        VolumeInfo entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new VolumeInfo();
            Elements volumes = doc.select("h3");
            for (Element volume : volumes) {
                String volumeName=volume.text().split("·")[0].split(" ")[volume.text().split("·")[0].split(" ").length-1];
                if(volumeName!=null && volumeName.trim().length()!=0){
                    //第几卷
                    entity.setVolumeNum(1);
                    //卷名称
                    entity.setVolumeName(volumeName);
                    //是否收费
                    //卷字数
                    entity.setWordsCount(0);
                    //章数
//                    entity.setChapteCnt(0);
                    entity.setBookId(bookId);
                    volumeId = CommomUtil.numId();
                    entity.setVolumeId(volumeId);
                    //识别码
                    entity.setUuid(CommomUtil.uuid());
                    //获取章节
                    entity.setChapterInfos(crawlNovelChapterList(doc));
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
    public List<VolumeInfo> crawlNovelVolumeList(Document doc) {
        List<VolumeInfo> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<VolumeInfo>();
            int i=1;
            for (Element e : doc.select(".volume")) {
                VolumeInfo v=crawlNovelVolume(Jsoup.parse(e.toString()));
                v.setVolumeNum(i);
                list.add(v);
                i++;
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
    public ChapterInfo crawlNovelChapter(Document doc) {
        ChapterInfo entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new ChapterInfo();

                //章节名称
                entity.setChapterName(doc.text());
                //章节url
                String url= ReptileUtil.getUrl(doc);
                //章节首发时间
                entity.setUpdateTime(new Date());

                entity.setVolumeId(volumeId);

                //识别码
                entity.setUuid(CommomUtil.uuid());
                String content=ReptileUtil.getContent(url);
                //获取章节里的内容
                entity.setContent(content.getBytes());
                //章节字数
                entity.setWordsCount((long) content.length());
//                entity.setContent("".getBytes());
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
    public List<ChapterInfo> crawlNovelChapterList(Document doc) {
        List<ChapterInfo> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<ChapterInfo>();
            int i=1;//章节序列
            long prev=0;//上一章id
            long next=0;
            for (Element e : doc.select(".cf li a")) {

                ChapterInfo c=crawlNovelChapter(Jsoup.parse(e.toString()));

                if(prev!=0){
                    c.setPrev(prev);
                }
                if(next!=0){
                    c.setChapterId(next);
                }else{
                    c.setChapterId(CommomUtil.numId());
                }
                next = CommomUtil.numId();
                c.setNext(next);
                prev=c.getChapterId();
                //第几章
                c.setChapterNum(i);
                list.add(c);
                i++;
            }
        }

        return list;
    }





}
