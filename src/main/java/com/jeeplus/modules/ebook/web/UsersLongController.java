package com.jeeplus.modules.ebook.web;

import com.jeeplus.modules.ebook.entity.SnowflakeIdWorker;
import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.service.UsersLongService;
import com.jeeplus.modules.ebook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.jeeplus.modules.ebook.utils.MD5.encodeMD5;

@Controller
public class UsersLongController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersLongService usersLongService;

    //注册
    @RequestMapping("/Logon")
    public String Logon(Users users) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
//        Long.parseLong();
        users.setUserid(id + "");//获取用户id
        String md5 = encodeMD5("a123456");
        users.setPassword(md5);
        return null;
    }

    //登录
    @RequestMapping("/Login")
    @ResponseBody
    public Map Login(String username, String password, HttpSession session) {

        System.out.println("username ===>" + username);
        System.out.println("password ===>" + password);
        Map map = usersLongService.selectOneUsers(username, password);
        session.setAttribute("rows", map);
        return map;
    }

}
