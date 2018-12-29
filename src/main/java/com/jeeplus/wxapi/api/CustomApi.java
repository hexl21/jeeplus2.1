package com.jeeplus.wxapi.api;

public class CustomApi {

    // 获取客服基本信息
    public static final String GET_KF_LIST = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=%s";

    // 添加客服帐号
    public static final String ADD = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=%s";

    //邀请绑定客服帐号
    public static final String INVITE_WORKER = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=%s";

    //设置客服信息
    public static final String UPDATE = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=%s";

    //上传客服头像 https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
    public static final String UPLOAD_HEADING = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=%s&kf_account=%s";

    // 删除客服帐号
    public static final String DEL = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=%s&kf_account=%s";
}
