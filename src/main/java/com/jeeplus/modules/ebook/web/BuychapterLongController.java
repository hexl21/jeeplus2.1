package com.jeeplus.modules.ebook.web;

import com.jeeplus.modules.ebook.entity.Buychapter;
import com.jeeplus.modules.ebook.entity.Chapter;
import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.service.BuychapterLongService;
import com.jeeplus.modules.ebook.service.BuychapterService;
import com.jeeplus.modules.ebook.service.ChapterLongService;
import com.jeeplus.modules.ebook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BuychapterLongController {

    @Autowired
    private BuychapterService buychapterService;

    @Autowired
    private UsersService usersService;
    @Autowired
    private BuychapterLongService buychapterLongService;
    @Autowired
    private ChapterLongService chapterLongService;

    @RequestMapping("/selectOneBuychapter")
    @ResponseBody
    public Map selectOneBuychapter(String chapterid, HttpSession session) {
        Map rows = (Map) session.getAttribute("rows");
        Users users = (Users) rows.get("users");
        String id = users.getId();
        System.out.println("123213213213213213====>  " + id);
        Map map = buychapterLongService.selectOneBuychapter(chapterid, id);
        return map;
    }

    @RequestMapping("/judgeMoney")
    @ResponseBody
    public Map judgeMoney(String id, HttpSession session) {
        Map rows = (Map) session.getAttribute("rows");
        Users users = (Users) rows.get("users");
        String money = users.getMoney();
        Map map = chapterLongService.judgeMoney(id, money);
        return map;
    }

    @RequestMapping("/insertChapterAndUpdateUsers")
    @ResponseBody
    public Map insertChapterAndUpdateUsers(Buychapter buychapter, Map maps, HttpSession session) {
        String chapterid = buychapter.getChapterid();
        Chapter chapter = buychapterLongService.selectMoney(chapterid);
        String chapterMoney = chapter.getMoney();
        buychapter.setChaptermoney(chapterMoney);
        String bookName = chapter.getBooks().getBookName();//获取书名
        buychapter.setBookname(bookName);
        String chapterMoney1 = chapter.getMoney();
        String chapterName = chapter.getName();
        buychapter.setChaptername(chapterName);//章节名
        int cmoney = Integer.parseInt(chapterMoney1);//章节书币


        //获取用户id
        Map rows = (Map) session.getAttribute("rows");
        Users users = (Users) rows.get("users");
        String id = users.getId();
        String money = users.getMoney();
        buychapter.setUserid(id);//获取用户id
        //--

        int umoney = Integer.parseInt(money);//用户书币
        int ucmoney = umoney - cmoney;//用户书币-章节书币
        String moneyu = String.valueOf(ucmoney);
        users.setMoney(moneyu);
        maps.put("users", users);
        session.setAttribute("rows", maps);

        usersService.save(users);
        buychapterService.save(buychapter);
        Map map = new HashMap();
        map.put("ok", "ok");
        return map;

    }

    @RequestMapping("/selectAllBuychapter")
    public String selectAllBuychapter(Map maps) {
        List<Buychapter> buychapterList = buychapterLongService.selectAllBuychapter();
        maps.put("buychapterList", buychapterList);
        return "forward:/webpage/E-books/personal-consum.jsp";
    }
}
