package com.jeeplus.modules.ebook.web;

import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.ebook.entity.Enshrine;
import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.service.EnshrineLongService;
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
public class EnshrineLongController {
    @Autowired
    private HistoryLongService historyLongService;

    @Autowired
    private EnshrineLongService enshrineLongService;


    @RequestMapping("/insertEnshrine")
    @ResponseBody
    public void insertEnshrine(Enshrine enshrine, HttpSession session) {

        Books books = historyLongService.selectOneBooks(enshrine.getBookid());
        enshrine.setBookid(books.getId());
        enshrine.setBookname(books.getBookName());
        enshrine.setBookpic(books.getBookPic());

        Map map = (Map) session.getAttribute("rows");
        Users users = (Users) map.get("users");
        String uuids = UUID.randomUUID().toString().replaceAll("\\-", "");
        enshrine.setId(uuids);
        enshrine.setUserid(users.getId());
        Date day = new Date();
        enshrine.setDate(day);
        System.out.println("enshrine  ==>  " + enshrine);
        enshrineLongService.insertEnshrine(enshrine);


    }

    @RequestMapping("/selectAllEnshrine")
    public String selectAllEnshrine(Map map, HttpSession session) {
        Map umap = (Map) session.getAttribute("rows");
        Users users = (Users) umap.get("users");
        String userid1 = users.getId();
        List<Enshrine> enshrines = enshrineLongService.selectAllEnshrine(userid1);
        System.out.println("enshrines  ++++++  " + enshrines);
        map.put("eash", enshrines);
        return "forward:/webpage/E-books/personal-collection.jsp";
    }

    @RequestMapping("/daleteOneBookEnshrine")
    @ResponseBody
    public boolean daleteOneBookEnshrine(String id) {

        return enshrineLongService.daleteOneBookEnshrine(id);
    }

    @RequestMapping("/selectOneEnshrine")
    @ResponseBody
    public Map selectOneEnshrine(String chapterid) {
        System.out.println("chapterid +---+ " + chapterid);
        return enshrineLongService.selectOneEnshrine(chapterid);
    }
}
