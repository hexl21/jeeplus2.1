package com.jeeplus.wxapi.api;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.wxapi.exception.WxError;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.util.HttpClientUtils;

public class DatacubeApi {

    // 获取用户增减数据
    public static final String GET_USER_SUMMARY = "https://api.weixin.qq.com/datacube/getusersummary?access_token=%s";

    // 获取累计用户数据
    public static final String GET_USER_CUMULATE = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=%s";

    //获取消息发送概况数据
    public static final String GET_UPSTREAM_MSG = "https://api.weixin.qq.com/datacube/getupstreammsg?access_token=%s";

    // 获取消息分送分时数据（getupstreammsghour）	1	https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=ACCESS_TOKEN
    public static final String GET_UPSTREAM_MSG_HOUR = "https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=%s";

    // 获取消息发送分布数据（getupstreammsgdist）	15	https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=ACCESS_TOKEN
    public static final String GET_UPSTREAM_MSG_DIST = "https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=%s";

    //   获取消息发送分布月数据（getupstreammsgdistmonth）	30	https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token=ACCESS_TOKEN
    public static final String GET_UPSTREAM_MSG_DIST_MONTH = "https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token=%s";

    //   获取图文群发每日数据（getarticlesummary）	1	https://api.weixin.qq.com/datacube/getarticlesummary?access_token=ACCESS_TOKEN
    public static final String GET_ARTCLE_SUMMARY = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token=%s";

    //   获取图文群发总数据（getarticletotal）	1	https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN
    public static final String GET_ARTCLE_TOTAL = "https://api.weixin.qq.com/datacube/getarticletotal?access_token=%s";

    //  获取图文统计数据（getuserread）	3	https://api.weixin.qq.com/datacube/getuserread?access_token=ACCESS_TOKEN
    public static final String GET_USER_READ = "https://api.weixin.qq.com/datacube/getuserread?access_token=%s";

    //  获取图文统计分时数据（getuserreadhour）	1	https://api.weixin.qq.com/datacube/getuserreadhour?access_token=ACCESS_TOKEN
    public static final String GET_USER_READ_HOUR = "https://api.weixin.qq.com/datacube/getuserreadhour?access_token=%s";

    //  获取图文分享转发数据（getusershare）	7	https://api.weixin.qq.com/datacube/getusershare?access_token=ACCESS_TOKEN
    public static final String GET_USER_SHARE = "https://api.weixin.qq.com/datacube/getusershare?access_token=%s";

    // 获取图文分享转发分时数据（getusersharehour）	1	https://api.weixin.qq.com/datacube/getusersharehour?access_token=ACCESS_TOKEN
    public static final String GET_USER_SHARE_HOUR = "https://api.weixin.qq.com/datacube/getusersharehour?access_token=%s";


    // 获取用户增减数据
    public static JSONObject getUserSummary(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(GET_USER_SUMMARY, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }

    // 获取累计用户数据
    public static JSONObject getUserCumulate(String token, String params) throws WxErrorException {
        String result = HttpClientUtils.sendHttpPost(String.format(GET_USER_CUMULATE, token), params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }
        return JSONObject.parseObject(result);
    }
}
