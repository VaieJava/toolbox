package com.outdd.toolbox;

import com.outdd.toolbox.common.util.HttpUtils;
import com.outdd.toolbox.common.util.io.NovelIo;
import com.outdd.toolbox.reptile.service.impl.IChapterInterImpl;
import org.junit.Test;

import java.io.IOException;

public class Test1 {
    @Test
    public void test1(){
        IChapterInterImpl ChapterInterImpl = new IChapterInterImpl();
        NovelIo no = new NovelIo();
//        List<Chapter> chapterList = ChapterInterImpl.getChapter("http://www.biquge.tw/0_5/");
//        for (Chapter chapter : chapterList) {
//            System.out.println(chapter);
//        }
//        String txt=ChapterInterImpl.getContent("https://www.xs.la/0_5/1373.html");
////        System.out.println(txt);
//        no.write(txt);
        ChapterInterImpl.asd("https://read.qidian.com/chapter/x1nzL3Er_ub36JmDw--oJQ2/R3GRIyw_o81OBDFlr9quQA2");
//        try {
//
//            String asd=HttpUtils.httpGetHeader("https://read.qidian.com/chapter/x1nzL3Er_ub36JmDw--oJQ2/27cFhnbkH3vM5j8_3RRvhw2",
//                    "{_csrfToken=NSvojIyAXR9B518rPpg5ErPCFIJ7RIG9a1e3qCHs, pageOps=1, newstatisticUUID=1541038589_1503931329}","");
//            System.out.println(asd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
