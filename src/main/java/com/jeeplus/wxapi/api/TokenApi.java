package com.jeeplus.wxapi.api;

public class TokenApi {
    // token 接口
    public static final String TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    // 获取token接口
    public static String getTokenUrl(String appId, String appSecret) {
        return String.format(TOKEN, appId, appSecret);
    }
}
