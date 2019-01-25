package com.jeeplus.modules.ebook.service.impl;

import com.jeeplus.modules.ebook.entity.Enshrine;
import com.jeeplus.modules.ebook.mapper.EnshrineLongMapper;
import com.jeeplus.modules.ebook.mapper.EnshrineMapper;
import com.jeeplus.modules.ebook.service.EnshrineLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnshrineLongServiceImpl implements EnshrineLongService {

    @Autowired
    private EnshrineMapper enshrineMapper;
    @Autowired
    private EnshrineLongMapper enshrineLongMapper;

    @Override
    public void insertEnshrine(Enshrine enshrine) {
        enshrineMapper.insert(enshrine);
    }

    @Override
    public List<Enshrine> selectAllEnshrine(String userid) {
        return enshrineLongMapper.selectAllEnshrine(userid);
    }

    @Override
    public boolean daleteOneBookEnshrine(String id) {
        boolean bool = false;
        try {
            enshrineLongMapper.daleteOneBookEnshrine(id);
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    @Override
    public Map selectOneEnshrine(String cid) {
        Map map = new HashMap();
        boolean bool = false;
        Enshrine enshrine = enshrineLongMapper.selectOneEnshrine(cid);
        System.out.println("selectOneEnshrine/(boolean)/enshrine" + enshrine);
        if (enshrine != null) {
            bool = true;
        } else {
            bool = false;
        }
        map.put("bool", bool);
        return map;
    }
}
