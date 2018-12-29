package com.jeeplus.wxapi.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.wxapi.exception.WxError;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.HttpMethod;
import com.jeeplus.wxapi.process.OAuthAccessToken;
import com.jeeplus.wxapi.util.HttpHelper;
import com.jeeplus.wxapi.util.Utils;

public class Oauth2Api {

    // 网页授权OAuth2.0获取code
    public static final String GET_OAUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";

    // 网页授权OAuth2.0获取token
    public static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    // 网页授权OAuth2.0获取用户信息
    public static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    // 网页授权OAuth2.0获取code
    public static String getOAuthCodeUrl(String appId, String redirectUrl, String scope, String state) {
        return String.format(GET_OAUTH_CODE, appId, Utils.urlEnodeUTF8(redirectUrl), "code", scope, state);
    }

    // 网页授权OAuth2.0获取token
    public static String getOAuthTokenUrl(String appId, String appSecret, String code) {
        return String.format(GET_OAUTH_TOKEN, appId, appSecret, code);
    }

    // 网页授权OAuth2.0获取用户信息
    public static String getOAuthUserinfoUrl(String token, String openid) {
        return String.format(GET_OAUTH_USERINFO, token, openid);
    }

    // 获取OAuth2.0 Token
    public static OAuthAccessToken getOAuthAccessToken(String appId, String appSecret, String code) throws WxErrorException {
        OAuthAccessToken token = null;
        String tockenUrl = getOAuthTokenUrl(appId, appSecret, code);
        JSONObject jsonObject = HttpHelper.httpsRequest(tockenUrl, HttpMethod.GET, null);
        if (null != jsonObject && !jsonObject.containsKey("errcode")) {
            try {
                token = new OAuthAccessToken();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getIntValue("expires_in"));
                token.setOpenid(jsonObject.getString("openid"));
                token.setScope(jsonObject.getString("scope"));
            } catch (JSONException e) {
                token = null;// 获取token失败
            }
        } else if (null != jsonObject) {
            throw new WxErrorException(WxError.fromJson(jsonObject));
        }
        return token;
    }
}
