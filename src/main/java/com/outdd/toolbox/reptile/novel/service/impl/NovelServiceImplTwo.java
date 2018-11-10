package com.outdd.toolbox.reptile.novel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.outdd.toolbox.common.util.CommomUtil;
import com.outdd.toolbox.reptile.novel.dao.NovelDetailsMapper;
import com.outdd.toolbox.reptile.novel.pojo.NovelChapter;
import com.outdd.toolbox.reptile.novel.pojo.NovelContent;
import com.outdd.toolbox.reptile.novel.pojo.NovelDetails;
import com.outdd.toolbox.reptile.novel.pojo.NovelVolume;
import com.outdd.toolbox.reptile.novel.service.NovelService;
import com.outdd.toolbox.reptile.novel.util.ReptileUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class NovelServiceImplTwo implements NovelService {
 String directoryRule = ".listmain dl dd a";
 String titleRule=".content h1";
 String contentsRule="#content";
 @Autowired
 NovelDetailsMapper novelDetailsMapper;
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

//获取小说详情信息
 public NovelDetails getNovelDetails(Document doc) {
  NovelDetails nd= new NovelDetails();


  if (CommomUtil.isNotNull(doc)) {
   Elements titleUrls = doc.select(".book-info");

   for (Element titleUrl : titleUrls) {
    nd.setCode(CommomUtil.uuid());
    nd.setTitle(titleUrl.child(0).child(0).text());
    nd.setAuthor(titleUrl.child(0).child(1).child(0).text());
    nd.setClassify(titleUrl.child(1).child(titleUrl.siblingIndex()-1).text()+""+titleUrl.child(1).child(titleUrl.siblingIndex()).text());
   }
   Elements intros = doc.select(".book-intro");

   for (Element intro : intros) {
    nd.setDescription(intro.text().replaceAll(" ", "\r\n") + "\r\n");
   }
  }
  return nd;
 }

//获取小说节章信息
 public List<NovelChapter> getNovelChapter(Element volume){
  List<NovelChapter> ncList = new ArrayList<NovelChapter>();

  if (CommomUtil.isNotNull(volume)) {
   Elements chapters = volume.select(".volume .cf li a");

   for (Element chapter : chapters) {
    NovelChapter nc = new NovelChapter();
    nc.setName(chapter.text());
    nc.setPremiereDate( new Date());
    nc.setCode(CommomUtil.uuid());
    nc.setNovelContent(getNovelContent("https:" + chapter.attr("href")));
    ncList.add(nc);
   }
  }

  return ncList;
 }
 //获取小说卷信息
 public List<NovelVolume> getNovelVolume(Document doc){
  List<NovelVolume> nvList = new ArrayList<NovelVolume>();

  if (CommomUtil.isNotNull(doc)) {
   Elements volumes = doc.select(".volume");

   for (Element volume : volumes) {
    String v=volume.child(1).text().split("·")[0].split(" ")[volume.text().split("·")[0].split(" ").length-1];
    if(v!=null && v.trim().length()!=0){
     NovelVolume nv = new NovelVolume();
     nv.setCode(CommomUtil.uuid());
     nv.setName(v);
     nvList.add(nv);
     nv.setNovelChapterList(getNovelChapter(volume));
    }
   }
  }

  return nvList;
 }

 //获取小说内容
 public NovelContent getNovelContent(String url) {
  NovelContent nt= new NovelContent();
  Document doc = ReptileUtil.getDocumentOfHttps(url);

  nt.setCode(CommomUtil.uuid());
  nt.setContent(ReptileUtil.getDetails(doc,".j_chapterName",".j_readContent").getBytes());
  return nt;
 }
}
