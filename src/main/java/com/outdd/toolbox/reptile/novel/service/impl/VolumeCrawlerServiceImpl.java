package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
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
 * TODO: 爬行接口-卷实现
 * @author VAIE
 * @date: 2018/11/12-9:51
 * @version v1.0
 */
@Service
public class VolumeCrawlerServiceImpl  extends ParentCrawlerServiceImpl<VolumeInfo>{
//    @Autowired
//    @Qualifier("chapterCrawlerServiceImpl")
//    CrawlerService<ChapterInfo> crawlerService;
    CrawlerService<ChapterInfo> crawlerService =new ChapterCrawlerServiceImpl();

    private int type=2;//1:起点实现 2：新趣笔阁实现 **实现开关
    private int isDownwards=1;//1:启动 2：不启动 **向下进行时开关
    private String volumeRule;//卷规则
    private String volumeNameRule;//卷规则
    private long volumeId;

    public VolumeCrawlerServiceImpl() {
        if(type == 1){
            volumeNameRule="h3";
            volumeRule=".volume";
        }else{
            volumeNameRule="dl dt";
            volumeRule="#list";
        }
    }

    /**
     * TODO: 爬取详情信息
     *
     * @param doc Document类
     * @return NovelDetails
     */
    @Override
    public VolumeInfo crawlInfo(Document doc) {
        VolumeInfo entity = null;
        if (CommomUtil.isNotNull(doc)) {
            entity = new VolumeInfo();
            Elements volumes = doc.select(volumeNameRule);
            for (Element volume : volumes) {
                String volumeName = getVolumeName(volume.text());
                if(volumeName!=null && volumeName.trim().length()!=0){
                    //卷名称
                    entity.setVolumeName(volumeName);
                    //是否收费
                    //卷字数
                    entity.setWordsCount(0);
                    volumeId = CommomUtil.numId();
                    entity.setVolumeId(volumeId);
                    //识别码
                    entity.setUuid(CommomUtil.uuid());
                    //获取章节
                    if(isDownwards == 1){
                        entity.setChapterInfos(crawlerService.crawlInfos(doc,entity.getVolumeId().toString()));
                    }

                }
            }
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
    public List<VolumeInfo> crawlInfos(Document doc,String parentId) {
        List<VolumeInfo> list = null;
        if (CommomUtil.isNotNull(doc)) {
            list = new ArrayList<VolumeInfo>();
            int i=1;
            for (Element e : doc.select(volumeRule)) {
                VolumeInfo v=crawlInfo(Jsoup.parse(e.toString()));
                v.setVolumeNum(i);
                v.setBookId(Long.valueOf(parentId));
                list.add(v);
                i++;
            }
        }
        return list;
    }

   String getVolumeName(String volumeName){
       if(type == 1){
           volumeName= volumeName.split("·")[0].split(" ")[volumeName.split("·")[0].split(" ").length-1];
       }
       return volumeName;
    }

}
