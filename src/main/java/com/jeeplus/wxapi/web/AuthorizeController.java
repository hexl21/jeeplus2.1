package com.jeeplus.wxapi.web;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.CookieUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.wxapi.exception.CustomRuntimeException;
import com.jeeplus.wxapi.process.HttpMethod;
import com.jeeplus.wxapi.process.OAuthAccessToken;
import com.jeeplus.wxapi.process.WxApi;
import com.jeeplus.wxapi.util.AdvancedUtil;
import com.jeeplus.wxapi.util.EmojiUtils;
import com.jeeplus.wxapi.util.HttpHelper;
import com.jeeplus.wxapi.util.Utils;
import com.jeeplus.wxapi.vo.SNSUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Controller
@RequestMapping(value = "/weixin/")
public class AuthorizeController {


    @RequestMapping("confirm.do")
    public void confirm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WxAccount wxAccount = (WxAccount) CacheUtils.get("WxAccount");//获取缓存中的唯一账号
        String turl = request.getParameter("turl");
        turl = Utils.decodeURL(turl);
        if (turl.toLowerCase().indexOf("/weixin/confirm.do") > -1) {
            // 避免循环加载页面
            throw new CustomRuntimeException("跳转页面非法");
        }
        CookieUtils.setCookie(response, "jjc_turl", Utils.encodeURL(turl));
        String redirect_uri = Utils.encodeURL(HttpHelper.getBaseRequestURI(request) + "/weixin/authorize.do");

        // String url = WxApi.getOAuthCodeUrl(wxAccount.getAppid(), redirect_uri, OAuthScope.Userinfo.toString(), "jjc");
        String fullUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxAccount.getAppid() + "&redirect_uri="
                + redirect_uri + "&response_type=code&scope=snsapi_userinfo&state=jjc#wechat_redirect";

        response.sendRedirect(fullUri);
    }

    @RequestMapping("authorize.do")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String turl = Utils.decodeURL(CookieUtils.getCookie(request, "jjc_turl"));
        WxAccount wxAccount = (WxAccount) CacheUtils.get("WxAccount");//获取缓存中的唯一账号
        if (StringUtils.isBlank(turl)) {
            throw new CustomRuntimeException("参数非法");
        }
        String code = request.getParameter("code");
        // System.out.println("code:"+code);
        if (StringUtils.isBlank(code)) {
            throw new CustomRuntimeException("参数非法");
        }

        String url = WxApi.getOAuthTokenUrl(wxAccount.getAppid(), wxAccount.getAppsecret(), code);


        JSONObject json = WxApi.httpsRequest(url, HttpMethod.GET, null);
        if (json.containsKey("errcode") && !json.getString("errcode").equals("0")) {
            throw new CustomRuntimeException("微信授权出错：" + json);
        }

        // getAccessToken(Utils.APPID,Utils.APPSECRET);

        String access_token = json.getString("access_token");// 获取access_token
        String openid = json.getString("openid");// 获取openid

        url = WxApi.getOAuthUserinfoUrl(access_token, openid);

        json = WxApi.httpsRequest(url, HttpMethod.GET, null);
        System.err.println(json);
        if (json.containsKey("errcode") && !json.getString("errcode").equals("0")) {
            response.sendRedirect(turl);
            return;

        }
        String headimgurl = json.getString("headimgurl");
        if (StringUtils.isNotBlank(headimgurl) && headimgurl.indexOf('/') > -1) {
            headimgurl = headimgurl.substring(0, headimgurl.lastIndexOf('/')) + "/132";
        }

        SNSUserInfo snsUserInfo = new SNSUserInfo();

//        Integer subscribe = json.getInt("subscribe");
//        if (subscribe.equals("0")) {
//
//        }
        //是否关注
        //    snsUserInfo.setSubscribe(json.getString("subscribe"));

        // 用户的标识
        snsUserInfo.setOpenId(json.getString("openid"));
        // 昵称（处理emoji表情）
        snsUserInfo.setNickname(EmojiUtils.filterEmoji(json.getString("nickname")));
        // 性别（1是男性，2是女性，0是未知）
        snsUserInfo.setSex(Integer.valueOf(json.getString("sex")));
        // 用户所在国家
        snsUserInfo.setCountry(json.getString("country"));
        // 用户所在省份
        snsUserInfo.setProvince(json.getString("province"));
        // 用户所在城市
        snsUserInfo.setCity(json.getString("city"));
        // 用户头像
        snsUserInfo.setHeadImgUrl(headimgurl);

        request.getSession().setAttribute("sns_user", snsUserInfo);
        // MemberLogin login = memberLoginService.getByOpenId(snsUserInfo.getOpenId());
        try {
//            if (login == null) {
//                login = new MemberLogin();
//                login.setOpenId(snsUserInfo.getOpenId());
//                login.setNickName(snsUserInfo.getNickname());
//                login.setRegisterTime(new Date());
//                login.setPic(snsUserInfo.getHeadImgUrl());
//                login.setLastLoginTime(new Date());
//                login.setSex(snsUserInfo.getSex() + "");
//                memberLoginService.save(login);
//            } else {
//                login.setOpenId(snsUserInfo.getOpenId());
//                login.setNickName(snsUserInfo.getNickname());
//                login.setPic(snsUserInfo.getHeadImgUrl());
//                login.setLastLoginTime(new Date());
//                login.setSex(snsUserInfo.getSex() + "");
//                login.setId(login.getId());
//                login.setLastLoginTime(new Date());
//                memberLoginService.save(login);
//            }
            request.getSession().setAttribute("MemberOpenId", snsUserInfo.getOpenId());
            response.sendRedirect(turl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 过滤特殊字符
    public String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";

        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        str = filterEmoji(str);// 过滤emoji表情
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    // 过滤emoji表情
    public String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
        return source;
    }

    @RequestMapping(value = "oauth2")
    public void WeixinOauth2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        WxAccount wxAccount = (WxAccount) CacheUtils.get("WxAccount");//获取缓存中的唯一账号
        // 用户同意授权
        if (!"authdeny".equals(code)) {

            OAuthAccessToken weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(wxAccount.getAppid(), wxAccount.getAppsecret(), code);
            String openId = weixinOauth2Token.getOpenid();


            String tockenUrl = WxApi.getTokenUrl(wxAccount.getAppid(), wxAccount.getAppsecret());
            JSONObject json = WxApi.httpsRequest(tockenUrl, HttpMethod.GET, null);
            String access_token = "";
            if (json != null) {
                access_token = json.getString("access_token");
                tockenUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&&openid=" + openId;
                json = WxApi.httpsRequest(tockenUrl, HttpMethod.GET, null);

                SNSUserInfo snsUserInfo = new SNSUserInfo();


                //是否关注
                String subscribe = json.getString("subscribe");
                String headimgurl = json.getString("headimgurl");
                if (StringUtils.isNotBlank(headimgurl) && headimgurl.indexOf('/') > -1) {
                    headimgurl = headimgurl.substring(0, headimgurl.lastIndexOf('/')) + "/132";
                }
                // 用户的标识
                snsUserInfo.setOpenId(json.getString("openid"));
                // 昵称（处理emoji表情）
                snsUserInfo.setNickname(EmojiUtils.filterEmoji(json.getString("nickname")));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(Integer.valueOf(json.getString("sex") == null ? "0" : json.getString("sex")));
                // 用户所在国家
                snsUserInfo.setCountry(json.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(json.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(json.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(headimgurl);
                if (subscribe.equals("0")) {
                    System.err.println("请关注公众号");
                    String requestUrl = Utils.decodeURL(HttpHelper.getBaseRequestURI(request) + "/concern.shtml");
                    response.sendRedirect(requestUrl);
                } else {
//                    MemberLogin login = memberLoginService.getByOpenId(snsUserInfo.getOpenId());
                    try {
//                        if (login == null) {
//                            login = new MemberLogin();
//                            login.setOpenId(snsUserInfo.getOpenId());
//                            login.setNickName(snsUserInfo.getNickname());
//                            login.setRegisterTime(new Date());
//                            login.setPic(snsUserInfo.getHeadImgUrl());
//                            login.setLastLoginTime(new Date());
//                            login.setSex(snsUserInfo.getSex() + "");
//                            memberLoginService.save(login);
//                        } else {
//                            login.setOpenId(snsUserInfo.getOpenId());
//                            login.setNickName(snsUserInfo.getNickname());
//                            login.setPic(snsUserInfo.getHeadImgUrl());
//                            login.setLastLoginTime(new Date());
//                            login.setSex(snsUserInfo.getSex() + "");
//                            login.setId(login.getId());
//                            login.setLastLoginTime(new Date());
//                            memberLoginService.save(login);
//                        }
                        request.getSession().setAttribute("sns_user", snsUserInfo);
                        //request.getSession().setAttribute("MemberId", login.getId());
                        request.getSession().setAttribute("MemberOpenId", snsUserInfo.getOpenId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CookieUtils.setCookie(response, "concern", "concern", 120);
                    String requestUrl = Utils.decodeURL(CookieUtils.getCookie(request, "requestUrl"));
                    response.sendRedirect(requestUrl);
                }
            }
        }
    }

}
