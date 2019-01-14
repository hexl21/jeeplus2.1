package com.jeeplus.modules.ebook.service.impl;

import com.jeeplus.modules.ebook.entity.History;
import com.jeeplus.modules.ebook.mapper.HistoryLongMapper;
import com.jeeplus.modules.ebook.mapper.HistoryMapper;
import com.jeeplus.modules.ebook.service.HistoryLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryLongServiceImpl implements HistoryLongService {
    @Autowired
    private HistoryLongMapper historyLongMapper;
    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public void insertHistory(History history) {
//        Books books = historyLongMapper.selectOneBooks(history.getBookid());
//        history.setBookid(books.getId());
//        history.setBookname(books.getBookName());
//        history.setBookpic(books.getBookPic());

        historyMapper.insert(history);
    }

    @Override
    public List<History> selectAllHistory(String userid) {
        return historyLongMapper.selectAllHistory(userid);
    }

    @Override
    public Boolean daleteOneBookHistory(String id) {
        Boolean bool = false;
        try {
            historyLongMapper.daleteOneBookHistory(id);
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }
}
