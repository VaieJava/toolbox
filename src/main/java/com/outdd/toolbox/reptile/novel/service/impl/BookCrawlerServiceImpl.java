package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.CrawlerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * TODO: 爬行接口-Book实现
 * @author VAIE
 * @date: 2018/11/12-9:34
 * @version v1.0
 */
@Service
public class BookCrawlerServiceImpl extends ParentCrawlerServiceImpl<BookInfo> {

    @Autowired
    @Qualifier("volumeCrawlerServiceImpl")
    CrawlerService<VolumeInfo> crawlerService;

    private int type=2;//1:起点实现 2：新趣笔阁实现 **实现开关
    private int isDownwards=1;//1:启动 2：不启动 **向下进行时开关
    /**
     * TODO: 爬取详情信息
     *
     * @param doc Document类
     * @return NovelDetails
     */
    @Override
    public BookInfo crawlInfo(Document doc) {
        BookInfo entity = null;
        if(type == 1){
            entity=qidianImpl(doc);
        }else {
            entity=xbiqugeImpl(doc);
        }

        return entity;
    }

    /**
     * TODO: 爬取多个详情信息
     *
     * @param doc Document类
     * @return List<NovelDetails> 多个
     */
    @Override
    public List<BookInfo> crawlInfos(Document doc,String parentId) {
        List<BookInfo> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<BookInfo>();
            for (Element e : doc.select("")) {
                list.add(crawlInfo(doc));
            }
        }
        return list;
    }

    /**
     * TODO:启点爬虫实现
     * @param doc
     * @return BookInfo
     */
    public BookInfo qidianImpl(Document doc){
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
                entity.setBookId(CommomUtil.numId());
                //是否完结
                //是否签约
                //是否收费
                //评分
                //识别码
                entity.setUuid(CommomUtil.uuid());
                //获取卷
                if(isDownwards == 1){
                    entity.setVolumeInfos(crawlerService.crawlInfos(doc,entity.getBookId().toString()));
                }
            }
        }
        return entity;
    }

    /**
     * TODO:新趣笔阁爬虫实现
     * @param doc
     * @return BookInfo
     */
    public BookInfo xbiqugeImpl(Document doc){
        BookInfo entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new BookInfo();
//            try {
//                Elements imgs = doc.select("#fmimg img");
//                String  src=imgs.get(0).attr("src");
//                //作品图片
//                entity.setBookImg(src.getBytes());
//            }catch (Exception e){
//
//            }

            entity.setBookName(doc.select("#title").text());
            Elements titleUrls = doc.select("#info");
            for (Element titleUrl : titleUrls) {
                //标题
                //作者
                entity.setAuthor(titleUrl.child(0).text());
                //分类
//                entity.setCateId(titleUrl.child(1).child(titleUrl.siblingIndex()-1).text()+""+titleUrl.child(1).child(titleUrl.siblingIndex()).text());
                //简介
                entity.setIntro(doc.select("#intro table div").text().replaceAll(" ", "\r\n") + "\r\n");

                //作品信息
                entity.setBookId(CommomUtil.numId());
                //是否完结
                //是否签约
                //是否收费
                //评分
                //识别码
                entity.setUuid(CommomUtil.uuid());
                //获取卷
                if(isDownwards == 1){
                    entity.setVolumeInfos(crawlerService.crawlInfos(doc,entity.getBookId().toString()));
                }
            }
        }
        return entity;
    }
}
