/*
 * FileName：WxMessageBuilder.java
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jeeplus.wxapi.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.weixin.entity.WxMsgBase;
import com.jeeplus.modules.weixin.entity.WxMsgNews;
import com.jeeplus.modules.weixin.entity.WxMsgNewsArticle;
import com.jeeplus.wxapi.vo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息builder工具类
 */
public class WxMessageBuilder {

    //客服文本消息
    public static String prepareCustomText(String openid, String content) {
        JSONObject jsObj = new JSONObject();
        jsObj.put("touser", openid);
        jsObj.put("msgtype", MsgType.Text.name());
        JSONObject textObj = new JSONObject();
        textObj.put("content", content);
        jsObj.put("text", textObj);
        return jsObj.toString();
    }

    //获取 MsgResponseText 对象
    public static MsgResponseText getMsgResponseBaseText(MsgRequest msgRequest, WxMsgBase msgText) {
        if (msgText != null) {
            MsgResponseText reponseText = new MsgResponseText();
            reponseText.setToUserName(msgRequest.getFromUserName());
            reponseText.setFromUserName(msgRequest.getToUserName());
            reponseText.setMsgType(MsgType.Text.toString());
            reponseText.setCreateTime(new Date().getTime());
            reponseText.setContent(msgText.getSubList().get(0).getRemarks());
            return reponseText;
        } else {
            return null;
        }
    }

    //获取 MsgResponseNews 对象
    public static MsgResponseNews getMsgResponseNews(MsgRequest msgRequest, WxMsgNews msgNews) {
        if (msgNews != null && msgNews.getArticleList().size() > 0) {
            MsgResponseNews responseNews = new MsgResponseNews();
            responseNews.setToUserName(msgRequest.getFromUserName());
            responseNews.setFromUserName(msgRequest.getToUserName());
            responseNews.setMsgType(MsgType.News.toString());
            responseNews.setCreateTime(new Date().getTime());
            responseNews.setArticleCount(msgNews.getArticleList().size());
            List<Article> articles = new ArrayList<Article>(msgNews.getArticleList().size());
            for (WxMsgNewsArticle n : msgNews.getArticleList()) {
                Article a = new Article();
                a.setTitle(n.getTitle());
                a.setPicUrl(n.getPicPath());
                if (StringUtils.isEmpty(n.getFromUrl())) {
                    a.setUrl(n.getUrl());
                } else {
                    a.setUrl(n.getFromUrl());
                }
                a.setDescription(n.getBrief());
                articles.add(a);
            }
            responseNews.setArticles(articles);
            return responseNews;
        } else {
            return null;
        }
    }

    //获取 MsgResponseVoice 对象
    public static MsgResponseVoice getMsgResponseVoice(MsgRequest msgRequest, Voice voice) {
        if (voice != null) {
            MsgResponseVoice reponseText = new MsgResponseVoice();
            reponseText.setToUserName(msgRequest.getFromUserName());
            reponseText.setFromUserName(msgRequest.getToUserName());
            reponseText.setMsgType(MsgType.Voice.toString());
            reponseText.setCreateTime(new Date().getTime());
            reponseText.setVoice(voice);
            return reponseText;
        } else {
            return null;
        }
    }

    //获取 MsgResponseImage 对象
    public static MsgResponseImage getMsgResponseImage(MsgRequest msgRequest, Image image) {
        if (image != null) {
            MsgResponseImage reponseText = new MsgResponseImage();
            reponseText.setToUserName(msgRequest.getFromUserName());
            reponseText.setFromUserName(msgRequest.getToUserName());
            reponseText.setMsgType(MsgType.Image.toString());
            reponseText.setCreateTime(new Date().getTime());
            reponseText.setImage(image);
            return reponseText;
        } else {
            return null;
        }
    }

    //获取 MsgResponseVideo 对象
    public static MsgResponseVideo getMsgResponseVideo(MsgRequest msgRequest, Video video) {
        if (video != null) {
            MsgResponseVideo reponseText = new MsgResponseVideo();
            reponseText.setToUserName(msgRequest.getFromUserName());
            reponseText.setFromUserName(msgRequest.getToUserName());
            reponseText.setMsgType(MsgType.Video.toString());
            reponseText.setCreateTime(new Date().getTime());
            reponseText.setVideo(video);
            return reponseText;
        } else {
            return null;
        }
    }

    //获取 MsgResponseMusic 对象
    public static MsgResponseMusic getMsgResponseMusic(MsgRequest msgRequest, Music music) {
        if (music != null) {
            MsgResponseMusic reponseText = new MsgResponseMusic();
            reponseText.setToUserName(msgRequest.getFromUserName());
            reponseText.setFromUserName(msgRequest.getToUserName());
            reponseText.setMsgType(MsgType.Music.toString());
            reponseText.setCreateTime(new Date().getTime());
            reponseText.setMusic(music);
            return reponseText;
        } else {
            return null;
        }
    }


    //客服图文消息
    public static String prepareCustomNews(String openid, WxMsgNews msgNews) {
        JSONObject jsObj = new JSONObject();
        jsObj.put("touser", openid);
        jsObj.put("msgtype", MsgType.MPNEWS.toString().toLowerCase());
        JSONObject media = new JSONObject();
        media.put("media_id", msgNews.getMediaId());
        jsObj.put("mpnews", media);

        return jsObj.toString();
    }

    public static void main(String[] args) {
        JSONObject jsObj = new JSONObject();
        jsObj.put("touser", 1);
        jsObj.put("msgtype", 2);
        JSONObject articles = new JSONObject();
        JSONArray articleArray = new JSONArray();
        JSONObject newsObj = new JSONObject();
        newsObj.put("title", 3);
        newsObj.put("description", 4);
        articleArray.add(newsObj);
        articles.put("articles", articleArray);
        jsObj.put("news", articles);
        System.out.println(jsObj.toString());
    }
}
