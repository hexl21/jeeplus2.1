package com.jeeplus.wxapi.api;

public class MeunApi {
    // 创建菜单
    public static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    // 创建个性化菜单
    public static final String MENU_ADDCONDITIONAL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=%s";

    // 删除菜单
    public static final String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";

    // 获取菜单创建接口
    public static String getMenuCreateUrl(String token) {
        return String.format(MENU_CREATE, token);
    }

    // 获取个性化菜单创建接口
    public static String getMenuAddconditionalUrl(String token) {
        return String.format(MENU_ADDCONDITIONAL, token);
    }

    // 获取菜单删除接口
    public static String getMenuDeleteUrl(String token) {
        return String.format(MENU_DELETE, token);
    }

}
