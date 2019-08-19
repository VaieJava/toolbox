package com.outdd.toolbox.reptile.novel.controller;

import com.outdd.toolbox.reptile.novel.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * TODO: 小说（音频）控制器
 * @author VAIE
 * @date: 2018/11/3-14:00
 * @version v1.0
 */
@Controller
@RequestMapping("/voiceBook/")
public class VoiceBookController {
    @Autowired
    NovelService novelService;
    String site = "novel/";
}
