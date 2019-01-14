package com.jeeplus.modules.ebook.service;

import com.jeeplus.modules.ebook.entity.History;

import java.util.List;

public interface HistoryLongService {
    public void insertHistory(History history);

    public List<History> selectAllHistory(String userid);

    public Boolean daleteOneBookHistory(String id);
}
