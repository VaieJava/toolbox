package com.outdd.toolbox;

import com.outdd.toolbox.reptile.novel.dao.NovelChapterMapper;
import com.outdd.toolbox.reptile.novel.dao.NovelDetailsMapper;
import com.outdd.toolbox.reptile.novel.dao.NovelVolumeMapper;
import com.outdd.toolbox.reptile.novel.pojo.NovelChapter;
import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.service.impl.NovelServiceImplTwo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages="com.outdd.toolbox.**.dao")
public class ToolboxApplicationTests {
    @Autowired
    NovelDetailsMapper novelDetailsMapper;
    @Autowired
    NovelVolumeMapper novelVolumeMapper;
    @Autowired
    NovelChapterMapper novelChapterMapper;
    @Test
    public void contextLoads() {
        NovelService ns = new NovelServiceImplTwo();
//        NovelDetails nd=((NovelServiceImplTwo) novelService).getNovelDetails("");
//        novelDetailsMapper.insertSelective(nd);

            novelVolumeMapper.insertBatch(((NovelServiceImplTwo) ns).getNovelVolume(""));


    }

}
