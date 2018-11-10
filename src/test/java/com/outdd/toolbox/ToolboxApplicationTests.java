package com.outdd.toolbox;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.reptile.novel.dao.*;
import com.outdd.toolbox.reptile.novel.pojo.*;
import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImplTwo;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "com.outdd.toolbox.**.dao")
public class ToolboxApplicationTests {
    @Resource
    NovelDetailsMapper novelDetailsMapper;
    @Resource
    NovelVolumeMapper novelVolumeMapper;
    @Resource
    NovelChapterMapper novelChapterMapper;
    @Resource
    MergeDetailsVolumeMapper mergeDetailsVolumeMapper;
    @Resource
    MergeVolumeChapterMapper mergeVolumeChapterMapper;
    @Resource
    MergeChapterContentMapper mergeChapterContentMapper;
    @Resource
    NovelContentMapper novelContentMapper;

    @Test
//    @Transactional
    public void contextLoads() {
        NovelService ns = new NovelServiceImplTwo();
//        NovelDetails nd=((NovelServiceImplTwo) novelService).getNovelDetails("");
//        novelDetailsMapper.insertSelective(nd);
        Document doc = ReptileUtil.getDocumentOfHttps("https://book.qidian.com/info/1012854291");
        NovelDetails nd = ((NovelServiceImplTwo) ns).getNovelDetails(doc);
//        List<NovelChapter> nclist = ((NovelServiceImplTwo) ns).getNovelChapter(doc);
        List<NovelVolume> nvlist = ((NovelServiceImplTwo) ns).getNovelVolume(doc);
        novelDetailsMapper.insertSelective(nd);
        novelVolumeMapper.insertBatch(nvlist);

        List<MergeDetailsVolume> mdvlist = new ArrayList<MergeDetailsVolume>();

        for(NovelVolume nv:nvlist){
            MergeDetailsVolume mdv =new MergeDetailsVolume();
            mdv.setDetailsCode(nd.getCode());
            mdv.setVolumeCode(nv.getCode());
            mdvlist.add(mdv);
            List<MergeVolumeChapter> mvcList = new ArrayList<MergeVolumeChapter>();
            novelChapterMapper.insertBatch(nv.getNovelChapterList());
            for (NovelChapter nc:nv.getNovelChapterList()){
                novelContentMapper.insert(nc.getNovelContent());
                MergeChapterContent mcc=new MergeChapterContent();
                mcc.setChapterCode(nc.getCode());
                mcc.setContentCode(nc.getNovelContent().getCode());
                mergeChapterContentMapper.insert(mcc);
                MergeVolumeChapter mvc= new MergeVolumeChapter();
                mvc.setVolumeCode(nv.getCode());
                mvc.setChapterCode(nc.getCode());
                mvcList.add(mvc);
                mergeVolumeChapterMapper.insertBatch(mvcList);
                mvcList.clear();
            }

        }
        int i=0;
        mergeDetailsVolumeMapper.insertBatch(mdvlist);
//        System.out.println("一共成功"+i+"条数据");


    }
    @Test
    public void query() {
        NovelDetails nd=novelDetailsMapper.selectByPrimaryKey("ee1f56cafc064ff89a1efe58ff753379");
        System.out.println(JSONObject.toJSON(nd));
    }

}
