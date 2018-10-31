package com.outdd.toolbox;

import com.outdd.toolbox.common.util.io.NovelIo;
import com.outdd.toolbox.reptile.service.impl.IChapterInterImpl;
import org.junit.Test;

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
        ChapterInterImpl.asd("https://read.qidian.com/chapter/x1nzL3Er_ub36JmDw--oJQ2/27cFhnbkH3vM5j8_3RRvhw2");
    }

}
