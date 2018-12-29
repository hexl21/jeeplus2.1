package com.jeeplus.wxapi.util;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.HttpMethod;
import com.jeeplus.wxapi.process.JSTicket;
import com.jeeplus.wxapi.process.WxApi;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeixinUtil {
    /**
     * 方法名：getWxConfig</br>
     * 详述：获取微信的配置信息 </br>
     * 开发人员：souvc </br>
     * 创建时间：2016-1-5 </br>
     *
     * @param request
     * @return 说明返回值含义
     * @throws
     */
    public static Map<String, Object> getWxConfig(HttpServletRequest request) throws WxErrorException {
        Map<String, Object> ret = new HashMap<String, Object>();
        WxAccount wxAccount = (WxAccount) CacheUtils.get("WxAccount");//获取缓存中的唯一账号

        String requestUrl = request.getParameter("requestUrl");
//		String queryString = request.getQueryString();
//		requestUrl = requestUrl + "?" + queryString;
        String access_token = "";
        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
        String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串

        String tockenUrl = WxApi.getTokenUrl(wxAccount.getAppid(), wxAccount.getAppsecret());
        JSONObject json = WxApi.httpsRequest(tockenUrl, HttpMethod.GET, null);

        JSTicket jsTicket = null;
        if (json != null) {
            // 要注意，access_token需要缓存
            access_token = json.getString("access_token");
            if (json != null) {
                jsapi_ticket = json.getString("ticket");
            }
            jsTicket = WxApi.getJSTicket(access_token);

        }
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        String sign = "jsapi_ticket=" + jsTicket.getTicket() + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url="
                + requestUrl;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("appId", wxAccount.getAppid());
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        return ret;
    }


    /**
     * 方法名：byteToHex</br>
     * 详述：字符串加密辅助方法 </br>
     * 开发人员：souvc </br>
     * 创建时间：2016-1-5 </br>
     *
     * @param hash
     * @return 说明返回值含义
     * @throws
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;

    }
}
