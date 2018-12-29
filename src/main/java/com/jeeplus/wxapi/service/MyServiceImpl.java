/*
 * FileName：MyServiceImpl.java
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
package com.jeeplus.wxapi.service;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.weixin.entity.*;
import com.jeeplus.modules.weixin.mapper.WxFansMapper;
import com.jeeplus.modules.weixin.mapper.WxMediaFilesMapper;
import com.jeeplus.modules.weixin.mapper.WxMenuMapper;
import com.jeeplus.modules.weixin.service.WxMsgBaseService;
import com.jeeplus.modules.weixin.service.WxMsgNewsService;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.MsgType;
import com.jeeplus.wxapi.process.MsgXmlUtil;
import com.jeeplus.wxapi.process.WxApiClient;
import com.jeeplus.wxapi.process.WxMessageBuilder;
import com.jeeplus.wxapi.util.WxUtil;
import com.jeeplus.wxapi.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务消息处理
 * 开发者根据自己的业务自行处理消息的接收与回复；
 */

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private WxMsgBaseService msgBaseDao;
    @Autowired
    private WxMsgNewsService msgNewsDao;
    @Autowired
    private WxFansMapper fansDao;
    @Autowired
    private WxMediaFilesMapper filesDao;
    @Autowired
    private WxMenuMapper wxMenuDao;

    private Logger logger = Logger.getLogger(MyServiceImpl.class);

    /**
     * 处理消息
     * 开发者可以根据用户发送的消息和自己的业务，自行返回合适的消息；
     *
     * @param msgRequest : 接收到的消息
     * @param mpAccount  ： appId
     */
    public String processMsg(MsgRequest msgRequest, WxAccount mpAccount) throws WxErrorException {
        String msgtype = msgRequest.getMsgType();// 接收到的消息类型
        String respXml = null;// 返回的内容；
        if (msgtype.equals(MsgType.Text.toString())) {
            /**
             * 文本消息，一般公众号接收到的都是此类型消息
             */
            WxMsgBase base = new WxMsgBase();
            base.setMsgType("2");
            base.setInputCode(msgRequest.getContent());
            List<WxMsgBase> list = msgBaseDao.findList(base);
            WxMsgBase text = new WxMsgBase();
            if (list.size() > 0) {
                text = msgBaseDao.get(list.get(0).getId());
            }
            if (text != null) {
//                if (text.getRule().equals("1") && text.getInputCode().equals(msgRequest.getContent())) {
//                    respXml = MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseBaseText(msgRequest, text));
//                }
//                if (text.getRule().equals("0") && text.getInputCode().indexOf(msgRequest.getContent()) > -1) {
//
//                    respXml = MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseBaseText(msgRequest, text));
//                }8
                List<WxMsgBaseSub> subList = text.getSubList();
                WxMediaFiles files = new WxMediaFiles();
                for (WxMsgBaseSub s : subList) {
                    switch (s.getForeignType()) {//消息类型
                        case "1"://文本
                            respXml = MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseBaseText(msgRequest, text));
                            break;
                        case "2"://图文
                            WxMsgNews msgNews = msgNewsDao.get(s.getForeignId());
                            if (msgNews != null) {
                                respXml = MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
                            }
                            break;
                        case "3"://图片
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Image image = new Image();
                                image.setMediaId(files.getMediaId());
                                respXml = MsgXmlUtil.imageToXml(WxMessageBuilder.getMsgResponseImage(msgRequest, image));
                            }
                            break;
                        case "4"://语音
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Voice voice = new Voice();
                                voice.setMediaId(files.getMediaId());
                                respXml = MsgXmlUtil.voiceToXml(WxMessageBuilder.getMsgResponseVoice(msgRequest, voice));
                            }
                            break;
                        case "5"://视频
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Video video = new Video();
                                video.setMediaId(files.getMediaId());
                                respXml = MsgXmlUtil.videoToXml(WxMessageBuilder.getMsgResponseVideo(msgRequest, video));
                            }
                            break;
                    }
                }
            }
        } else if (msgtype.equals(MsgType.Event.toString())) {// 事件消息
            respXml = this.processEventMsg(msgRequest, mpAccount);
        } else if (msgtype.equals(MsgType.Image.toString())) {// 图片消息

        } else if (msgtype.equals(MsgType.Location.toString())) {// 地理位置消息

        }
        // 默认回复
        if (StringUtils.isEmpty(respXml)) {
            WxMsgBase base = new WxMsgBase();
            base.setMsgType("0");
            WxMsgBase text = msgBaseDao.getByCode(base);
            if (text != null) {
                List<WxMsgBaseSub> subList = text.getSubList();
                WxMediaFiles files = new WxMediaFiles();
                for (WxMsgBaseSub s : subList) {
                    switch (s.getForeignType()) {//消息类型
                        case "1"://文本
                            respXml = MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseBaseText(msgRequest, text));
                            break;
                        case "2"://图文
                            WxMsgNews msgNews = msgNewsDao.get(s.getForeignId());

                            if (msgNews != null) {
                                respXml = MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
                            }
                            break;
                        case "3"://图片
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Image image = new Image();
                                image.setMediaId(files.getMediaId());
                                respXml = MsgXmlUtil.imageToXml(WxMessageBuilder.getMsgResponseImage(msgRequest, image));
                            }
                            break;
                        case "4"://语音
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Voice voice = new Voice();
                                voice.setMediaId(files.getMediaId());
                                respXml = MsgXmlUtil.voiceToXml(WxMessageBuilder.getMsgResponseVoice(msgRequest, voice));
                            }
                            break;
                        case "5"://视频
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Video video = new Video();
                                video.setMediaId(files.getMediaId());
                                respXml = MsgXmlUtil.videoToXml(WxMessageBuilder.getMsgResponseVideo(msgRequest, video));
                            }
                            break;
                    }
                }
            }
        }
        return respXml;
    }

    // 处理文本消息
    private String processTextMsg(MsgRequest msgRequest, WxAccount mpAccount) {
        String content = msgRequest.getContent();
        if (!StringUtils.isEmpty(content)) {// 文本消息
            String tmpContent = content.trim();
            List<WxMsgNews> msgNews =null;// msgNewsDao.getRandomMsgByContent(tmpContent, mpAccount.getMsgCount());
            if (!CollectionUtils.isEmpty(msgNews)) {
                return MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews.get(0)));
            }
        }
        return null;
    }

    // 处理事件消息
    private String processEventMsg(MsgRequest msgRequest, WxAccount mpAccount) throws WxErrorException {
        String key = msgRequest.getEventKey();
        if (MsgType.SUBSCRIBE.toString().equals(msgRequest.getEvent())) {// 订阅消息
            logger.info("关注者openId----------" + msgRequest.getFromUserName());
            String openId = msgRequest.getFromUserName();
            WxFans fans = WxApiClient.syncAccountFans(openId, mpAccount);
            // 用户关注微信公众号后更新粉丝表
            if (null != fans) {
                WxFans tmpFans = fansDao.getByOpenId(openId);
                if (tmpFans == null) {
                    fans.setAccount(mpAccount.getAccount());
                    fans.preInsert();
                    fansDao.insert(fans);
                } else {
                    fans.setId(tmpFans.getId());
                    fans.preUpdate();
                    fansDao.update(fans);
                }
            }
            WxMsgBase base = new WxMsgBase();
            base.setMsgType("1");
            WxMsgBase text = msgBaseDao.getByCode(base);
            if (text != null) {
                List<WxMsgBaseSub> subList = text.getSubList();
                WxMediaFiles files = new WxMediaFiles();
                for (WxMsgBaseSub s : subList) {
                    switch (s.getForeignType()) {//消息类型
                        case "1"://文本
                            MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseBaseText(msgRequest, text));
                            break;
                        case "2"://图文
                            WxMsgNews msgNews = msgNewsDao.get(s.getForeignId());
                            if (msgNews != null) {
                                return MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
                            }
                            break;
                        case "3"://图片
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Image image = new Image();
                                image.setMediaId(files.getMediaId());
                                return MsgXmlUtil.imageToXml(WxMessageBuilder.getMsgResponseImage(msgRequest, image));
                            }
                            break;
                        case "4"://语音
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Voice voice = new Voice();
                                voice.setMediaId(files.getMediaId());
                                return MsgXmlUtil.voiceToXml(WxMessageBuilder.getMsgResponseVoice(msgRequest, voice));
                            }
                            break;
                        case "5"://视频
                            files = filesDao.get(s.getForeignId());
                            if (files != null) {
                                Video video = new Video();
                                video.setMediaId(files.getMediaId());
                                return MsgXmlUtil.videoToXml(WxMessageBuilder.getMsgResponseVideo(msgRequest, video));
                            }
                            break;
                    }
                }
                return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseBaseText(msgRequest, text));
            }

        } else if (MsgType.UNSUBSCRIBE.toString().equals(msgRequest.getEvent())) {// 取消订阅消息

//            WxMsgText text = null;// msgTextDao.getMsgTextByInputCode(MsgType.UNSUBSCRIBE.toString());
//            if (text != null) {
//                return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, text));
//            }

        } else {// 点击事件消息
//            if (!StringUtils.isEmpty(key)) {
//                /**
//                 * 固定消息
//                 * _fix_ ：在我们创建菜单的时候，做了限制，对应的event_key 加了 _fix_
//                 *
//                 * 当然开发者也可以进行修改
//                 */
//                if (key.startsWith("_fix_")) {
//                    String baseIds = key.substring("_fix_".length());
//                    if (!StringUtils.isEmpty(baseIds)) {
//                        String[] idArr = baseIds.split(",");
//                        if (idArr.length > 1) {// 多条图文消息
////                            List<WxMsgNews> msgNews = msgNewsDao.listMsgNewsByBaseId(idArr);
////                            if (msgNews != null && msgNews.size() > 0) {
////                                return MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
////                            }
//                        } else {// 图文消息，或者文本消息
//                            WxMsgBase msg = msgBaseDao.get(baseIds);
//                            if (msg.getMsgType().equals(MsgType.Text.toString())) {
//                                WxMsgText text = null;// msgTextDao.getMsgTextByBaseId(baseIds);
//                                if (text != null) {
//                                    return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, text));
//                                }
//                            } else {
////                                List<WxMsgNews> msgNews = msgNewsDao.listMsgNewsByBaseId(idArr);
////                                if (msgNews != null && msgNews.size() > 0) {
////                                    return MsgXmlUtil
////                                            .newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, msgNews));
////                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
        return null;
    }

    // 发布菜单
    public JSONObject publishMenu(WxAccount mpAccount) throws WxErrorException {
        // 获取数据库菜单
        List<WxMenu> menus = wxMenuDao.findAllList(new WxMenu());
        Matchrule matchrule = new Matchrule();
        String menuJson = JSONObject.toJSONString(WxUtil.prepareMenus(menus, matchrule));
        logger.info("创建菜单传参如下:" + menuJson);
        JSONObject rstObj = WxApiClient.publishMenus(menuJson, mpAccount);// 创建普通菜单
        logger.info("创建菜单返回消息如下:" + rstObj.toString());
        return rstObj;
    }
}
