package com.jeeplus.modules.ebook.service.impl;

import com.jeeplus.modules.ebook.entity.Chapter;
import com.jeeplus.modules.ebook.mapper.ChapterLongMapper;
import com.jeeplus.modules.ebook.service.ChapterLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChapterLongServiceImpl implements ChapterLongService {
    @Autowired
    private ChapterLongMapper chapterLongMapper;


    @Override
    public Chapter selectOneChapter(String id) {
        return chapterLongMapper.selectOneChapter(id);
    }
}
