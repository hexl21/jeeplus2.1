package com.jeeplus.modules.ebook.service;

import com.jeeplus.modules.ebook.entity.Chapter;

import java.util.Map;

public interface ChapterLongService {
    Chapter selectOneChapter(String id);

    Map selectOneChapterCharge(String id);

    Map judgeMoney(String chapterid, String money);
}
