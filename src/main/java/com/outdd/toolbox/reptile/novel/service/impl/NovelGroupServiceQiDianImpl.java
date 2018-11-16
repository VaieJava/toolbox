package com.outdd.toolbox.reptile.novel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.reptile.novel.dao.BookInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.ChapterInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.VolumeInfoMapper;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.reptile.novel.service.CrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
                List<ChapterInfo> vclist=vi.getChapterInfos();
                if(vclist.size()>100){
                    List<List<ChapterInfo>> acc=averageAssign(vclist);
                    for(List<ChapterInfo> a:acc){
                        chapterInfoMapper.insertBatch(a);
                    }
                }else{
                    chapterInfoMapper.insertBatch(vclist);
                }

            }
        return false;
        }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source){
        int singleNum=10;//每个list有多少个
        int count=source.size();
        int openList= (int) Math.ceil((double)count/singleNum);//需要分割成多数list
        int n=openList;
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }


}
