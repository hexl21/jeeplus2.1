package com.jeeplus.modules.ebook.web;

import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.ebook.entity.History;
import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.mapper.HistoryLongMapper;
import com.jeeplus.modules.ebook.service.HistoryLongService;
import com.jeeplus.modules.ebook.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class HistoryLongController {
    @Autowired
    private HistoryLongService historyLongService;
    @Autowired
    private HistoryLongMapper historyLongMapper;
    @Autowired
    private HistoryService historyService;

    @RequestMapping("/insertHistory")
    public void insertHistory(History history, HttpSession session) {
        Books books = historyLongMapper.selectOneBooks(history.getBookid());
        history.setBookid(books.getId());
        history.setBookname(books.getBookName());
        history.setBookpic(books.getBookPic());

        Map map = (Map) session.getAttribute("rows");
        Users users = (Users) map.get("users");
        String uuids = UUID.randomUUID().toString().replaceAll("\\-", "");
        history.setId(uuids);
        history.setUserid(users.getId());
        Date day = new Date();
        history.setDate(day);

        historyLongService.insertHistory(history);


//        historyService.save(history);
        System.out.println("history  ==>  " + history);
    }

    @RequestMapping("/selectAllHistory")
    public String selectAllHistory(Map map, HttpSession session) {
        Map umap = (Map) session.getAttribute("rows");
        Users users = (Users) umap.get("users");
        String userid1 = users.getId();
        List<History> histories = historyLongService.selectAllHistory(userid1);
        map.put("hist", histories);
        return "forward:/webpage/E-books/personal-history.jsp";
    }

    @RequestMapping("/daleteOneBookHistory")
    @ResponseBody
    public Boolean daleteOneBookHistory(String id) {
        System.out.println("```" + id);
        return historyLongService.daleteOneBookHistory(id);
    }
}
