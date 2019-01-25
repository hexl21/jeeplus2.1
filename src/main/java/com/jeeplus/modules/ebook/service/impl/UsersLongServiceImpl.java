package com.jeeplus.modules.ebook.service.impl;

import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.mapper.UsersLongMapper;
import com.jeeplus.modules.ebook.service.UsersLongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.jeeplus.modules.ebook.utils.MD5.encodeMD5;

@Service
public class UsersLongServiceImpl implements UsersLongService {
    @Autowired
    private UsersLongMapper usersLongMapper;

    @Override
    public Map selectOneUsers(String username, String password) {
        Map map = new HashMap();
        if (username != null && password != null) {
            System.out.println("password==>" + password);
            String md5 = encodeMD5(password);
            System.out.println("md5===>" + md5);
            Users users1 = usersLongMapper.selectOneUsers(username, md5);
            if (users1 != null) {

            }
            map.put("users", users1);

        } else {
            map.put("users", "用户名或密码不能为空！");

        }

        return map;
    }

    @Override
    public String selectOneUsersPassword(String password) {

        return usersLongMapper.selectOneUsersPassword(password);
    }
}
