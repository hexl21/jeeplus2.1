/**
 * Copyright &copy; 2015-2020 党群通管理系统
 */
package com.jeeplus.modules.weixin.statistics;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.service.WxMsgBaseService;
import com.jeeplus.modules.weixin.statistics.vo.MsgText;
import com.jeeplus.wxapi.process.WxApiClient;
import com.jeeplus.wxapi.util.DateUtilOld;
import com.jeeplus.wxapi.util.JSONUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文本消息Controller
 *
 * @author toteny
 * @version 2018-06-06
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/statistics")
public class MsgTextStatisticsController extends BaseController {


    @Autowired
    private WxMsgBaseService wxMsgBaseService;


    /**
     * 文本消息列表页面
     */
    @RequestMapping(value = {"msgStatis"})
    public String msgStatis(Model model) {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        JSONObject params = new JSONObject();
        params.put("begin_date", DateUtilOld.getBeforeDay7(new Date()));
        params.put("end_date", DateUtilOld.getBeforeDay(new Date()));
        try {
            JSONObject jsonObject = WxApiClient.getupstreammsg(account, JSONUtil.writeObject2Json(params));
            List<MsgText> list = (List) json2ObjectList(jsonObject.getString("list"), MsgText.class);
            ArrayList ref_date = new ArrayList();
            ArrayList msg_user = new ArrayList();
            ArrayList msg_count = new ArrayList();
            for (MsgText f : list) {
                ref_date.add(f.getRef_date());
                msg_user.add(f.getMsg_user());
                msg_count.add(f.getMsg_count());
            }
            model.addAttribute("ref_date", JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("msg_user", JSONUtil.writeObject2Json(msg_user));
            model.addAttribute("msg_count", JSONUtil.writeObject2Json(msg_count));
            params = new JSONObject();
            params.put("begin_date", DateUtilOld.getBeforeDay(new Date(), 1));
            params.put("end_date", DateUtilOld.getBeforeDay(new Date(), 1));

            jsonObject = WxApiClient.getupstreammsghour(account, JSONUtil.writeObject2Json(params));
            list = (List) json2ObjectList(jsonObject.getString("list"), MsgText.class);
            ref_date = new ArrayList();
            msg_user = new ArrayList();
            msg_count = new ArrayList();
            ArrayList ref_hour = new ArrayList();
            for (MsgText f : list) {
                ref_date.add(f.getRef_date());
                msg_user.add(f.getMsg_user());
                msg_count.add(f.getMsg_count());
                ref_hour.add(Integer.valueOf(f.getRef_hour()) / 100 + ":00");
            }
            System.out.println(JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("ref_date1", JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("msg_user1", JSONUtil.writeObject2Json(msg_user));
            model.addAttribute("msg_count1", JSONUtil.writeObject2Json(msg_count));
            model.addAttribute("ref_hour", JSONUtil.writeObject2Json(ref_hour));

            params = new JSONObject();
            params.put("begin_date", DateUtilOld.getBeforeDay(new Date(), 15));
            params.put("end_date", DateUtilOld.getBeforeDay(new Date(), 1));

            jsonObject = WxApiClient.getupstreammsgdist(account, JSONUtil.writeObject2Json(params));
            list = (List) json2ObjectList(jsonObject.getString("list"), MsgText.class);
            ref_date = new ArrayList();
            ArrayList count_interval = new ArrayList();
            msg_user = new ArrayList();
            for (MsgText f : list) {
                ref_date.add(f.getRef_date());
                count_interval.add(f.getCount_interval());
                msg_user.add(f.getMsg_user());
            }
            System.out.println(JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("ref_date2", JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("msg_user2", JSONUtil.writeObject2Json(msg_user));
            model.addAttribute("count_interval", JSONUtil.writeObject2Json(count_interval));

            params = new JSONObject();
            params.put("begin_date", DateUtilOld.getBeforeDay(new Date(), 30));
            params.put("end_date", DateUtilOld.getBeforeDay(new Date(), 1));

            jsonObject = WxApiClient.getupstreammsgdistmonth(account, JSONUtil.writeObject2Json(params));
            list = (List) json2ObjectList(jsonObject.getString("list"), MsgText.class);
            ref_date = new ArrayList();
            msg_user = new ArrayList();
            for (MsgText f : list) {
                ref_date.add(f.getRef_date());
                count_interval.add(f.getCount_interval());
                msg_user.add(f.getMsg_user());
            }
            System.out.println(JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("ref_date3", JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("msg_user3", JSONUtil.writeObject2Json(msg_user));
            model.addAttribute("count_interval3", JSONUtil.writeObject2Json(count_interval));



        } catch (Exception e) {
            e.printStackTrace();
        }
        return "modules/weixin/statistics/msgStatis";
    }

    private static Object json2ObjectList(String strJson, @SuppressWarnings("rawtypes") Class beanClass) {
        return JSONArray.toCollection(JSONArray.fromObject(strJson), beanClass);
    }
}