/*
 * FileName：WxApiCtrl.java
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
package com.jeeplus.wxapi.ctrl;

import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.wxapi.process.MsgXmlUtil;
import com.jeeplus.wxapi.service.MyService;
import com.jeeplus.wxapi.util.SignUtil;
import com.jeeplus.wxapi.vo.MsgRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Set;


/**
 * 微信与开发者服务器交互接口
 */
@Controller
@RequestMapping("/wxapi")
public class WxApiCtrl extends BaseController {

    private static Logger log = LogManager.getLogger(WxApiCtrl.class);

    @Autowired
    private MyService myService;

    /**
     * GET请求：进行URL、Tocken 认证；
     * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     */
    @RequestMapping(value = "/{account}/message", method = RequestMethod.GET)
    public @ResponseBody  String doGet(HttpServletRequest request, @PathVariable String account) {
        //如果是多账号，根据url中的account参数获取对应的MpAccount处理即可

        Set<String> keySet = request.getParameterMap().keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            //如果存在，则调用next实现迭代  
            String key = iterator.next();
            log.info("key: " + key + " value: " + request.getParameterMap().get(key));
        }
        WxAccount mpAccount = (WxAccount) CacheUtils.get("WxAccount");
        if (mpAccount != null) {
            String token = mpAccount.getToken();//获取token，进行验证；
            String signature = request.getParameter("signature");// 微信加密签名
            String timestamp = request.getParameter("timestamp");// 时间戳
            String nonce = request.getParameter("nonce");// 随机数
            String echostr = request.getParameter("echostr");// 随机字符串

            // 校验成功返回  echostr，成功成为开发者；否则返回error，接入失败
            if (SignUtil.validSign(signature, token, timestamp, nonce)) {
                return echostr;
            }
        }
        return "error";
    }

    /**
     * POST 请求：进行消息处理；
     */
    @RequestMapping(value = "/{account}/message", method = RequestMethod.POST)
    public @ResponseBody
    String doPost(HttpServletRequest request, @PathVariable String account, HttpServletResponse response) {
        //处理用户和微信公众账号交互消息
        WxAccount mpAccount = (WxAccount) CacheUtils.get("WxAccount");
        try {
            MsgRequest msgRequest = MsgXmlUtil.parseXml(request);//获取发送的消息
            return myService.processMsg(msgRequest, mpAccount);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return "error";
        }
    }
}
