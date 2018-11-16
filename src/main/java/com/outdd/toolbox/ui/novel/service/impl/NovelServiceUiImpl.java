package com.outdd.toolbox.ui.novel.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.outdd.toolbox.reptile.novel.dao.BookInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.ChapterInfoMapper;
import com.outdd.toolbox.reptile.novel.dao.VolumeInfoMapper;
import com.outdd.toolbox.reptile.novel.pojo.BookInfo;
import com.outdd.toolbox.reptile.novel.pojo.ChapterInfo;
import com.outdd.toolbox.reptile.novel.pojo.VolumeInfo;
import com.outdd.toolbox.ui.novel.service.NovelServiceUi;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/* * TODO: 接口实现
 * @author VAIE
 * @date: 2018/11/9-15:52
 * @version v1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class NovelServiceUiImpl implements NovelServiceUi {
    @Resource
    BookInfoMapper bookInfoMapper;
    @Resource
    VolumeInfoMapper volumeInfoMapper;
    @Resource
    ChapterInfoMapper chapterInfoMapper;
    @Override
    public BookInfo getBookInfo(String id) {

        return bookInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BookInfo> getBookInfo(BookInfo book) {

        return bookInfoMapper.selectByEntity(book);
    }

    @Override
    public PageInfo<BookInfo> getBookInfo(PageInfo page) {
        PageHelper.startPage(page);
        List<BookInfo> bookInfoList =bookInfoMapper.selectByEntity();
        return new PageInfo<>(bookInfoList);
    }


    @Override
    public VolumeInfo getVolumeInfo(String volumeId) {


        return volumeInfoMapper.selectByPrimaryKey(volumeId);
    }

    public ChapterInfo getChapter(String volumeId, String chapterId){
        ChapterInfo ci= new ChapterInfo();
        ci.setVolumeId(Long.valueOf(volumeId));
        ci.setChapterId(Long.valueOf(chapterId));
        try {
            ci=chapterInfoMapper.selectByEntity(ci).get(0);
        }catch (Exception e){

        }

        return ci;
    }
}
