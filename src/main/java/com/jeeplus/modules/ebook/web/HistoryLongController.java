package com.jeeplus.modules.ebook.web;

import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.ebook.entity.History;
import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.service.HistoryLongService;
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


    //添加历史

    @RequestMapping("/insertHistory")
    @ResponseBody
    public void insertHistory(History history, HttpSession session) {
        Books books = historyLongService.selectOneBooks(history.getBookid());
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


//        System.out.println("history  ==>  " + history);
    }

    //查询所有历史
    @RequestMapping("/selectAllHistory")
    public String selectAllHistory(Map map, HttpSession session) {
        Map umap = (Map) session.getAttribute("rows");
        Users users = (Users) umap.get("users");
        String userid1 = users.getId();
        List<History> histories = historyLongService.selectAllHistory(userid1);
        System.out.println(histories);
        map.put("hist", histories);
        return "forward:/webpage/E-books/personal-history.jsp";
    }

    //删除历史
    @RequestMapping("/daleteOneBookHistory")
    @ResponseBody
    public Boolean daleteOneBookHistory(String id) {
        System.out.println("```" + id);
        return historyLongService.daleteOneBookHistory(id);
    }


}
