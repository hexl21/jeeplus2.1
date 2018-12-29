package com.jeeplus.wxapi.api;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.wxapi.exception.WxError;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.util.HttpClientUtils;

public class FansApi {
    // 获取账号粉丝信息
    public static final String GET_FANS_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    // 获取账号粉丝列表
    public static final String GET_FANS_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s";

    // 设置用户备注名
    public static final String UPDATE_REMARK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=%s";

    //拉黑用户
    public static final String BATCH_BLACK_LIST = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=%s";

    //取消拉黑用户
    public static final String BATCH_UNBLACK_LIST = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=%s";

    // 获取粉丝信息接口
    public static String getFansInfoUrl(String token, String openid) {
        return String.format(GET_FANS_INFO, token, openid);
    }

    // 获取粉丝列表接口
    public static String getFansListUrl(String token, String nextOpenId) {
        if (nextOpenId == null) {
            return String.format(GET_FANS_LIST, token);
        } else {
            return String.format(GET_FANS_LIST + "&next_openid=%s", token, nextOpenId);
        }
    }


    //修改备注
    public static JSONObject updateremark(String token, String params) throws WxErrorException {

        String result = HttpClientUtils.sendHttpPost(String.format(UPDATE_REMARK, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }


    // 拉黑用户
    public static JSONObject batchBlackList(String token, String params) throws WxErrorException {

        String result = HttpClientUtils.sendHttpPost(String.format(BATCH_BLACK_LIST, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }


    // 取消拉黑用户
    public static JSONObject batchUnBlackList(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(BATCH_UNBLACK_LIST, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

}
