package com.jeeplus.modules.ebook.web;

import com.jeeplus.modules.ebook.entity.Chapter;
import com.jeeplus.modules.ebook.service.ChapterLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ChapterLongController {

    @Autowired
    private ChapterLongService chapterLongService;

    @RequestMapping("/pageSkip")
    public String pageSkip(String id, Map map) {
        Chapter chapter = chapterLongService.selectOneChapter(id);
        map.put("ids", id);
        map.put("chapter", chapter);
        System.out.println("id  =*********=> " + id);
//        System.out.println("chapter  =*********=> " + chapter);
        return "forward:/webpage/E-books/book-details-c.jsp";
    }

    @RequestMapping("/selectOneChapterCharge")
    @ResponseBody
    public Map selectOneChapterCharge(String id) {

        return chapterLongService.selectOneChapterCharge(id);
    }
}
