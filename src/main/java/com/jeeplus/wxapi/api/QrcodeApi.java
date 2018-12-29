package com.jeeplus.wxapi.api;

public class QrcodeApi {
    // 生成二维码
    public static final String CREATE_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

    // 根据ticket获取二维码图片
    public static final String SHOW_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

    // 获取创建二维码接口url
    public static String getCreateQrcodeUrl(String token) {
        return String.format(CREATE_QRCODE, token);
    }

    // 获取显示二维码接口
    public static String getShowQrcodeUrl(String ticket) {
        return String.format(SHOW_QRCODE, ticket);
    }

    /**
     * 获取创建临时二维码post data
     *
     * @param expireSecodes 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     * @param scene         临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
     * @return
     */
    public static String getQrcodeJson(Integer expireSecodes, Integer scene) {
        String postStr = "{\"expire_seconds\":%d,\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}";
        return String.format(postStr, expireSecodes, scene);
    }

    /**
     * 获取创建临时二维码post data
     *
     * @param scene 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
     * @return
     */
    public static String getQrcodeLimitJson(Integer scene) {
        String postStr = "{\"action_name\":\"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}";
        return String.format(postStr, scene);
    }

    // 获取永久二维码
    public static String getQrcodeLimitJson(String sceneStr) {
        String postStr = "{\"action_name\":\"QR_LIMIT_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":%s}}";
        return String.format(postStr, sceneStr);
    }
}
