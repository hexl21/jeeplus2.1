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
            String md5 = encodeMD5(password);
            Users users1 = usersLongMapper.selectOneUsers(username, md5);
            map.put("users", users1);

        } else {
            map.put("users", "用户名或密码错误！");

        }

        return map;
    }
}
