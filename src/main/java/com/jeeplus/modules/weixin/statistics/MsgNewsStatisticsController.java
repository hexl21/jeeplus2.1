/**
 * Copyright &copy; 2015-2020 党群通管理系统
 */
package com.jeeplus.modules.weixin.statistics;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.entity.WxMsgNews;
import com.jeeplus.modules.weixin.service.WxMsgNewsService;
import com.jeeplus.modules.weixin.statistics.vo.Article;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 图文信息Controller
 *
 * @author toteny
 * @version 2018-06-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/statistics")
public class MsgNewsStatisticsController extends BaseController {

    @Autowired
    private WxMsgNewsService wxMsgNewsService;

    @ModelAttribute
    public WxMsgNews get(@RequestParam(required = false) String id) {
        WxMsgNews entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxMsgNewsService.get(id);
        }
        if (entity == null) {
            entity = new WxMsgNews();
        }
        return entity;
    }

    /**
     * 图文信息列表页面
     */
    @RequestMapping(value = {"newStatis"})
    public String newStatis(Model model) {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        JSONObject params = new JSONObject();
        params.put("begin_date", DateUtilOld.getBeforeDay(new Date(), 3));
        params.put("end_date", DateUtilOld.getBeforeDay(new Date(), 1));
        try {
            JSONObject jsonObject = WxApiClient.getuserread(account, JSONUtil.writeObject2Json(params));
            List<Article> list = (List) json2ObjectList(jsonObject.getString("list"), Article.class);
            ArrayList title = new ArrayList();
            ArrayList int_page_read_user = new ArrayList();
            ArrayList ref_date = new ArrayList();
            ArrayList int_page_read_count = new ArrayList();
            ArrayList ori_page_read_user = new ArrayList();
            ArrayList ori_page_read_count = new ArrayList();
            ArrayList share_user = new ArrayList();
            ArrayList share_count = new ArrayList();
            ArrayList add_to_fav_user = new ArrayList();
            ArrayList add_to_fav_count = new ArrayList();
            for (Article a : list) {
                title.add(a.getTitle());
                ref_date.add(a.getRef_date());
                int_page_read_user.add(a.getInt_page_read_user());
                int_page_read_count.add(a.getInt_page_read_count());
                ori_page_read_user.add(a.getOri_page_read_user());
                ori_page_read_count.add(a.getOri_page_read_count());
                share_user.add(a.getShare_user());
                share_count.add(a.getShare_count());
                add_to_fav_user.add(a.getAdd_to_fav_user());
                add_to_fav_count.add(a.getAdd_to_fav_count());
            }
            model.addAttribute("title", JSONUtil.writeObject2Json(title));
            model.addAttribute("ref_date", JSONUtil.writeObject2Json(ref_date));
            model.addAttribute("int_page_read_user", JSONUtil.writeObject2Json(int_page_read_user));
            model.addAttribute("int_page_read_count", JSONUtil.writeObject2Json(int_page_read_count));
            model.addAttribute("ori_page_read_user", JSONUtil.writeObject2Json(ori_page_read_user));
            model.addAttribute("ori_page_read_count", JSONUtil.writeObject2Json(ori_page_read_count));
            model.addAttribute("share_user", JSONUtil.writeObject2Json(share_user));
            model.addAttribute("share_count", JSONUtil.writeObject2Json(share_count));
            model.addAttribute("add_to_fav_user", JSONUtil.writeObject2Json(add_to_fav_user));
            model.addAttribute("add_to_fav_count", JSONUtil.writeObject2Json(add_to_fav_count));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "modules/weixin/statistics/newStatis";
    }


    private static Object json2ObjectList(String strJson, @SuppressWarnings("rawtypes") Class beanClass) {
        return JSONArray.toCollection(JSONArray.fromObject(strJson), beanClass);
    }
}