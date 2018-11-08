package com.outdd.toolbox.reptile.novel.service.impl;

import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public class NovelServiceImplTwo implements NovelService {
 String directoryRule = ".listmain dl dd a";
 String titleRule=".content h1";
 String contentsRule="#content";
 @Override
 public void getNovelByAll(String url) {

 }

 @Override
 public String getNovelByOne(String url,String title) {
  List<Document> fdList=ReptileUtil.getDirectoryAll(url,title,directoryRule);
  return ReptileUtil.DocumentToTxt(fdList,titleRule,contentsRule);
 }
 @Override
 public List<Map<String, String>> getNovelByName(String NovelName) {
  return null;
 }
}
