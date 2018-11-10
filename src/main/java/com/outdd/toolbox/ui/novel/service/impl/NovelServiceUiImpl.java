package com.outdd.toolbox.ui.novel.service.impl;
import com.outdd.toolbox.reptile.novel.dao.MergeDetailsVolumeMapper;
import com.outdd.toolbox.reptile.novel.dao.NovelDetailsMapper;
import com.outdd.toolbox.reptile.novel.dao.NovelVolumeMapper;
import com.outdd.toolbox.reptile.novel.pojo.MergeDetailsVolume;
import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import com.outdd.toolbox.ui.novel.service.NovelServiceUi;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/* * TODO: 接口实现
 * @author VAIE
 * @date: 2018/11/9-15:52
 * @version v1.0
 */
@Slf4j
@Service
public class NovelServiceUiImpl implements NovelServiceUi {
    @Resource
    private NovelDetailsMapper novelDetailsMapper;
    @Resource
    private MergeDetailsVolumeMapper mergeDetailsVolumeMapper;
    @Resource
    private NovelVolumeMapper novelVolumeMapper;


    @Override
    public NovelDetails getNovelInfo(String id) {
        NovelDetails nd=novelDetailsMapper.selectByPrimaryKey(id);
        return nd;
    }

    @Override
    public List<NovelVolume> getNovelVolume(String id) {
        List<MergeDetailsVolume> mdvList=mergeDetailsVolumeMapper.selectByPrimaryKey(id);
        List<NovelVolume> nvList=novelVolumeMapper.selectByPrimaryKeyList(mdvList);
        return nvList;
    }
}
