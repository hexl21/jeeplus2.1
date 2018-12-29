package com.jeeplus.wxapi.api;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.wxapi.exception.WxError;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.util.HttpClientUtils;

public class TagsApi {
    //获取公众号已创建的标签
    public static final String GET_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=%s";
    //创建标签
    public static final String CREATE_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=%s";
    //更新标签
    public static final String UPDATE_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=%s";
    //删除标签
    public static final String DELETE_TAGS = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=%s";
    //获取标签下粉丝列表
    public static final String USER_TAGS = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=%s";
    //批量为用户打标签
    public static final String BATCHTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=%s";
    //批量为用户取消标签
    public static final String BATCHUNTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=%s";
    //获取用户身上的标签列表
    public static final String GET_ID_LIST = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=%s";

    public static final String MESSAGE_SENDALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=%s";


    //获取公众号已创建的标签
    public static String get_tags(String token) {
        return String.format(GET_TAGS, token);
    }

    //创建标签
    public static JSONObject create_tags(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(CREATE_TAGS, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    //更新标签
    public static JSONObject update_tags(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(UPDATE_TAGS, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    //删除标签
    public static JSONObject delete_tags(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(DELETE_TAGS, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    //获取标签下粉丝列表
    public static JSONObject user_tags(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(USER_TAGS, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    //批量为用户打标签
    public static JSONObject batchtagging(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(BATCHTAGGING, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    //批量为用户取消标签
    public static JSONObject batchuntagging(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(BATCHUNTAGGING, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    //获取用户身上的标签列表
    public static JSONObject getidlist_tags(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(GET_ID_LIST, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    //根据标签进行群发 https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN
    public static JSONObject message_sendall(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(MESSAGE_SENDALL, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }
}
