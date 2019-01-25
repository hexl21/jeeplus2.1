package com.jeeplus.modules.ebook.service.impl;

import com.jeeplus.modules.ebook.entity.Chapter;
import com.jeeplus.modules.ebook.mapper.ChapterLongMapper;
import com.jeeplus.modules.ebook.service.ChapterLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChapterLongServiceImpl implements ChapterLongService {
    @Autowired
    private ChapterLongMapper chapterLongMapper;


    @Override
    public Chapter selectOneChapter(String id) {
        return chapterLongMapper.selectOneChapter(id);
    }

    @Override
    public Map selectOneChapterCharge(String id) {
        Map map = new HashMap();
        String charge = chapterLongMapper.selectOneChapterCharge(id);
        map.put("charge", charge);
        return map;
    }

    @Override
    public Map judgeMoney(String chapterid, String umoney) {
        Map map = new HashMap();
        String cmoney = chapterLongMapper.judgeMoney(chapterid);
        int ui = Integer.parseInt(umoney);
        int ci = Integer.parseInt(cmoney);

        if ((ui - ci) >= 0) {
            map.put("bool", true);
        } else {
            map.put("bool", false);

        }
        return map;
    }
}
