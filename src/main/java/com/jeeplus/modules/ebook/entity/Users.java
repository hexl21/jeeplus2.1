/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.entity;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 管理用户Entity
 *
 * @author 高龙
 * @version 2019-01-12
 */
public class Users extends DataEntity<Users> {

    private static final long serialVersionUID = 1L;
    private String userid;        // 用户ID
    private String username;        // 用户名
    private String password;        // 密码
    private String money;        // 书币
    private String portraitpic;        // 头像
    private String state;        // 用户状态

    public Users() {
        super();
    }

    public Users(String id) {
        super(id);
    }

    @ExcelField(title = "用户ID", align = 2, sort = 6)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @ExcelField(title = "用户名", align = 2, sort = 7)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ExcelField(title = "密码", align = 2, sort = 8)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ExcelField(title = "书币", align = 2, sort = 9)
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @ExcelField(title = "头像", align = 2, sort = 10)
    public String getPortraitpic() {
        return portraitpic;
    }

    public void setPortraitpic(String portraitpic) {
        this.portraitpic = portraitpic;
    }

    @ExcelField(title = "用户状态", dictType = "user_state", align = 2, sort = 11)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}