package com.jeeplus.modules.ebook.service.impl;

import com.jeeplus.modules.ebook.entity.Buychapter;
import com.jeeplus.modules.ebook.entity.Chapter;
import com.jeeplus.modules.ebook.mapper.BuychapterLongMapper;
import com.jeeplus.modules.ebook.service.BuychapterLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuychapterLongServiceImpl implements BuychapterLongService {

    @Autowired
    private BuychapterLongMapper buychapterLongMapper;

    @Override
    public Map selectOneBuychapter(String chapterid, String userid) {
        Map map = new HashMap();
        String s = buychapterLongMapper.selectOneBuychapter(chapterid, userid);
        if (s != null) {
            map.put("bool", true);
        } else if (s == null) {
            map.put("bool", false);
        }
        return map;
    }

    @Override
    public Chapter selectMoney(String chapterid) {

        return buychapterLongMapper.selectMoney(chapterid);
    }

    @Override
    public List<Buychapter> selectAllBuychapter() {
        return buychapterLongMapper.selectAllBuychapter();
    }

}
