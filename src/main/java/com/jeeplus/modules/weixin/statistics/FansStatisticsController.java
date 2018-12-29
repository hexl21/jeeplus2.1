/**
 * Copyright &copy; 2015-2020 党群通管理系统
 */
package com.jeeplus.modules.weixin.statistics;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.entity.WxFans;
import com.jeeplus.modules.weixin.service.WxFansService;
import com.jeeplus.modules.weixin.statistics.vo.Fans;
import com.jeeplus.wxapi.process.WxApiClient;
import com.jeeplus.wxapi.util.DateUtilOld;
import com.jeeplus.wxapi.util.JSONUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 微信粉丝Controller
 *
 * @author toteny
 * @version 2018-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/statistics")
public class FansStatisticsController extends BaseController {

    @Autowired
    private WxFansService wxFansService;

    @ModelAttribute
    public WxFans get(@RequestParam(required = false) String id) {
        WxFans entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxFansService.get(id);
        }
        if (entity == null) {
            entity = new WxFans();
        }
        return entity;
    }

    /**
     * 微信粉丝列表页面
     */
    @RequestMapping(value = {"fansStatis"})
    public String fansStatis(Model model) {
        WxFans wxFans = wxFansService.getFansBySex();
        model.addAttribute("wxFans", wxFans);

        List<WxFans> list = wxFansService.getFansListByProvince();
        String[] provinceStr = new String[list.size()];
        Integer[] provinceCount = new Integer[list.size()];
        int i = 0;
        for (WxFans f : list) {
            if (StringUtils.isEmpty(f.getProvince())) {
                f.setProvince("其他");
            }
            provinceStr[i] = f.getProvince();
            provinceCount[i] = f.getCount();
            i++;
        }
        System.out.println("provinceStr=" + JSONUtil.writeObject2Json(provinceStr));
        System.out.println("provinceCount=" + JSONUtil.writeObject2Json(provinceCount));

        model.addAttribute("provinceStr", JSONUtil.writeObject2Json(provinceStr));
        model.addAttribute("provinceCount", JSONUtil.writeObject2Json(provinceCount));

        return "modules/weixin/statistics/fansStatis";
    }

    @RequestMapping(value = {"fansAttention"})
    public String fansAttention(Model model) throws ParseException {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        JSONObject params = new JSONObject();
        params.put("begin_date", DateUtilOld.getBeforeDay7(new Date()));
        params.put("end_date", DateUtilOld.getBeforeDay(new Date()));
        try {
            JSONObject jsonObject = WxApiClient.getUserSummary(account, JSONUtil.writeObject2Json(params));

            List<Fans> list = (List) json2ObjectList(jsonObject.getString("list"), Fans.class);
            ArrayList date = new ArrayList();
            ArrayList add = new ArrayList();
            ArrayList cel = new ArrayList();
            for (Fans f : list) {
                date.add(f.getRef_date());
                add.add(f.getNew_user());
                cel.add(f.getCancel_user());
            }
            System.out.println(JSONUtil.writeObject2Json(date));
            model.addAttribute("date", JSONUtil.writeObject2Json(date));
            model.addAttribute("add", JSONUtil.writeObject2Json(add));
            model.addAttribute("cel", JSONUtil.writeObject2Json(cel));


            jsonObject = WxApiClient.getUserCumulate(account, JSONUtil.writeObject2Json(params));
            System.out.println(jsonObject.getString("list"));
            list = (List) json2ObjectList(jsonObject.getString("list"), Fans.class);
            ArrayList cum = new ArrayList();
            ArrayList date1 = new ArrayList();
            for (Fans f : list) {
                cum.add(f.getCumulate_user());
                date1.add(f.getRef_date());
            }

            model.addAttribute("cum", JSONUtil.writeObject2Json(cum));
            model.addAttribute("date1", JSONUtil.writeObject2Json(date1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "modules/weixin/statistics/fansAttention";
    }

    private static Object json2ObjectList(String strJson, @SuppressWarnings("rawtypes") Class beanClass) {
        return JSONArray.toCollection(JSONArray.fromObject(strJson), beanClass);
    }
}