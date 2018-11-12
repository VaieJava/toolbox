package com.outdd.toolbox.reptile.novel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.reptile.novel.dao.BookInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.ChapterInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.VolumeInfoMapper;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.CrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/*
 * TODO: 组合小说
 * @author VAIE
 * @date: 2018/11/9-23:41
 * @version v1.0
 */
@Service
public class NovelGroupServiceQiDianImpl implements NovelGroupService {
    @Autowired
    @Qualifier("bookCrawlerServiceImpl")
    CrawlerService<BookInfo> crawlerService;
    @Resource
    BookInfoMapper bookInfoMapper;
    @Resource
    VolumeInfoMapper volumeInfoMapper;
    @Resource
    ChapterInfoMapper chapterInfoMapper;
    /**
     * TODO:新增一本小说
     *
     * @param url 网络地址
     * @return
     */
    @Override
    @Transactional
    public boolean insetNovel(String url) {


            BookInfo bookInfo=crawlerService.crawlInfo(url);
//            String aa=JSONObject.toJSON(bookInfo).toString();
//            System.out.println(aa);
            bookInfoMapper.insert(bookInfo);
            volumeInfoMapper.insertBatch(bookInfo.getVolumeInfos());
            for(VolumeInfo vi:bookInfo.getVolumeInfos()){
                chapterInfoMapper.insertBatch(vi.getChapterInfos());
            }
        return false;
        }


}
