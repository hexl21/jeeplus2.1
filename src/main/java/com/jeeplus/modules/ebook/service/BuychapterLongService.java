package com.jeeplus.modules.ebook.service;

import com.jeeplus.modules.ebook.entity.Buychapter;
import com.jeeplus.modules.ebook.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface BuychapterLongService {
    public Map selectOneBuychapter(String chapterid, String userid);

    public Chapter selectMoney(String chapterid);

    public List<Buychapter> selectAllBuychapter();
}
