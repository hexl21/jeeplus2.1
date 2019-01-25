package com.jeeplus.modules.ebook.service;

import com.jeeplus.modules.ebook.entity.Enshrine;

import java.util.List;
import java.util.Map;

public interface EnshrineLongService {

    public void insertEnshrine(Enshrine enshrine);

    public List<Enshrine> selectAllEnshrine(String userid);

    public boolean daleteOneBookEnshrine(String id);

    public Map selectOneEnshrine(String cid);
}
