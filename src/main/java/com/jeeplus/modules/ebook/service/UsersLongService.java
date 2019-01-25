package com.jeeplus.modules.ebook.service;

import java.util.Map;

public interface UsersLongService {
    public Map selectOneUsers(String username, String password);

    public String selectOneUsersPassword(String password);
}
