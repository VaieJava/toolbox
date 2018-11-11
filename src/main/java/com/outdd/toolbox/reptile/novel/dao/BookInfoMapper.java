package com.outdd.toolbox.reptile.novel.dao;

import com.outdd.toolbox.reptile.novel.pojo.BookInfo;

import java.util.List;

public interface BookInfoMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(BookInfo record);

    int insertSelective(BookInfo record);

    BookInfo selectByPrimaryKey(String bookId);

    List<BookInfo> selectByEntity(BookInfo record);

    int updateByPrimaryKeySelective(BookInfo record);

    int updateByPrimaryKeyWithBLOBs(BookInfo record);

    int updateByPrimaryKey(BookInfo record);
}