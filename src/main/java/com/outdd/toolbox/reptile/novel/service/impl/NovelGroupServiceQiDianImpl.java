package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.reptile.novel.service.NovelCrawlerService;
import com.outdd.toolbox.reptile.novel.service.NovelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/*
 * TODO: 组合小说
 * @author VAIE
 * @date: 2018/11/9-23:41
 * @version v1.0
 */
@Service
public class NovelGroupServiceQiDianImpl implements NovelGroupService {
    @Autowired
    @Qualifier("novelCrawlerServiceQiDianImpl")
    NovelCrawlerService novelCrawlerService;
    /**
     * TODO:新增一本小说
     *
     * @param url 网络地址
     * @return
     */
    @Override
    public boolean insetNovel(String url) {
        return false;
    }
}
