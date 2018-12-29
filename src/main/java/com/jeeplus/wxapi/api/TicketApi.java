package com.jeeplus.wxapi.api;

public class TicketApi {
    // js ticket
    public static final String GET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
    // 获取js ticket url
    public static String getJsApiTicketUrl(String token) {
        return String.format(GET_JSAPI_TICKET, token);
    }
}
