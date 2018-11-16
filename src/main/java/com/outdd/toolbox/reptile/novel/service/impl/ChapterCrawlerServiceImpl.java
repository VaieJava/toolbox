package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * TODO: 爬行接口-章实现
 * @author VAIE
 * @date: 2018/11/12-9:54
 * @version v1.0
 */
@Service
public class ChapterCrawlerServiceImpl   extends ParentCrawlerServiceImpl<ChapterInfo> {
    private int type=2;//1:起点实现 2：新趣笔阁实现 **实现开关
    private String contentRule;//内容规则
    String chapterRuel;//章节规则


    public ChapterCrawlerServiceImpl(){
        if(type==1){
            chapterRuel=".cf li a";
            contentRule =".j_readContent";
        }else {
            chapterRuel=".ccss a";
            contentRule ="#content";
        }
    }
    /**
     * TODO: 爬取详情信息
     *
     * @param doc Document类
     * @return NovelDetails
     */
    @Override
    public ChapterInfo crawlInfo(Document doc) {
        ChapterInfo entity = null;
        if (CommomUtil.isNotNull(doc)) {
            //模块处理
            //章节url
//            String url= null;
//            if(type==1){
//                url=ReptileUtil.getUrl(doc);
//            }else {
//                url="http://www.pgyzw.com/html/78/78474/"+ReptileUtil.getUrl(doc);
//            }

            //实体类处理
            entity = new ChapterInfo();
            //章节名称
            entity.setChapterName(doc.text());
            //章节首发时间
            entity.setUpdateTime(new Date());
            //识别码
            entity.setUuid(CommomUtil.uuid());
            String content=ReptileUtil.getContent(url,contentRule);
            //获取章节里的内容
            entity.setContent(content.getBytes());
            //章节字数
            entity.setWordsCount((long) content.length());
        }
        return entity;
    }

    String url;
    /**
     * TODO: 爬取多个详情信息
     *
     * @param doc Document类
     * @return List<NovelDetails> 多个
     */
    @Override
    public List<ChapterInfo> crawlInfos(Document doc,String parentId) {
        List<ChapterInfo> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<ChapterInfo>();
            int sequence=1;//章节序列
            long prev=0;//上一章id
            long next=0;//下一章id

            for (Element e : doc.select(chapterRuel)) {
                url=e.select("a").get(0).absUrl("href");
                ChapterInfo chapterInfo=crawlInfo(Jsoup.parse(e.html()));

                if(prev!=0){
                    chapterInfo.setPrev(prev);
                }
                if(next!=0){
                    chapterInfo.setChapterId(next);
                }else{
                    chapterInfo.setChapterId(CommomUtil.numId());
                }
                next = CommomUtil.numId();
                chapterInfo.setNext(next);
                prev=chapterInfo.getChapterId();
                //第几章
                chapterInfo.setChapterNum(sequence);
                chapterInfo.setVolumeId(Long.valueOf(parentId));
                list.add(chapterInfo);
                sequence++;
            }
        }
        return list;
    }
}
