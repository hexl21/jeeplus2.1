package com.jeeplus.wxapi.api;

public class MessageApi {
    // 群发接口
    public static final String MASS_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=%s";

    // 发送客服消息
    public static final String SEND_CUSTOM_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    // 模板消息接口
    public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    // 获取发送客服消息 url
    public static String getSendCustomMessageUrl(String token) {
        return String.format(SEND_CUSTOM_MESSAGE, token);
    }

    // 获取发送模板消息 url
    public static String getSendTemplateMessageUrl(String token) {
        return String.format(SEND_TEMPLATE_MESSAGE, token);
    }
}
