/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.entity.WxMenu;
import com.jeeplus.modules.weixin.service.WxMenuService;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.menu.Button;
import com.jeeplus.wxapi.menu.CommonButton;
import com.jeeplus.wxapi.menu.ComplexButton;
import com.jeeplus.wxapi.menu.Menu;
import com.jeeplus.wxapi.service.MyService;
import com.jeeplus.wxapi.util.JSONUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义菜单Controller
 *
 * @author toteny
 * @version 2018-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMenu")
public class WxMenuController extends BaseController {
    @Autowired
    private MyService myService;
    @Autowired
    private WxMenuService wxMenuService;

    @ModelAttribute
    public WxMenu get(@RequestParam(required = false) String id) {
        WxMenu entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxMenuService.get(id);
        }
        if (entity == null) {
            entity = new WxMenu();
        }
        return entity;
    }

    /**
     * 自定义菜单列表页面
     */
    @RequiresPermissions("weixin:wxMenu:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxMenu wxMenu, Model model) {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxMenu.setAccount(account.getAccount());
        wxMenu.setParentId("0");
        List<WxMenu> list = wxMenuService.findListByParentId(wxMenu);
        ArrayList arrayList = new ArrayList();
        Map<String, Object> menu = Maps.newHashMap();
        Map<String, Object> button = Maps.newHashMap();
        JSONArray array = new JSONArray();
        Map<String, Object> map = Maps.newHashMap();
        for (WxMenu w : list) {
            JSONArray arrayq = new JSONArray();
            map = Maps.newHashMap();
            map.put("name", w.getName());
            w.setParentId(w.getId());
            List<WxMenu> childList = wxMenuService.findListByParentId(w);
            if (childList.size() > 0) {
                for (WxMenu m : childList) {
                    Map<String, Object> map1 = Maps.newHashMap();
                    map1.put("name", m.getName());
                    if (StringUtils.isNotEmpty(m.getUrl())) {
                        map1.put("url", m.getUrl());
                    } else {
                        map1.put("key", m.getInputCode());
                    }
                    map1.put("type", m.getType());
                    arrayq.add(map1);
                }
            } else {
                if (StringUtils.isNotEmpty(w.getUrl())) {
                    map.put("url", w.getUrl());
                } else {
                    map.put("key", w.getInputCode());
                }
                map.put("type", w.getType());
            }
            map.put("sub_button", arrayq);
            array.add(map);
        }
        button.put("button", array);
        menu.put("menu", button);
        System.out.println(JSONUtil.writeObject2Json(menu));
        model.addAttribute("menu", JSONUtil.writeObject2Json(menu));
        model.addAttribute("account", account);
        model.addAttribute("wxMenu", wxMenu);
        return "modules/weixin/menu/wxMenuList";
    }

    public static void main(String args[]) {
        Map<String, Object> menu = Maps.newHashMap();
        Map<String, Object> button = Maps.newHashMap();
        JSONArray array = new JSONArray();
        Map<String, Object> map = Maps.newHashMap();

        map.put("type", "click");
        map.put("name", "今日歌曲");
        map.put("key", "col_2");

        JSONArray arrayq = new JSONArray();
        Map<String, Object> map1 = Maps.newHashMap();
        map1.put("type", "click");
        map1.put("name", "今日歌曲1");
        map1.put("key", "col_2");
        arrayq.add(map1);

        Map<String, Object> map21 = Maps.newHashMap();
        map21.put("type", "click");
        map21.put("name", "今日歌曲1");
        map21.put("key", "col_2");
        arrayq.add(map21);

        array.add(map);

        Map<String, Object> map2 = Maps.newHashMap();
        map2.put("type", "view");
        map2.put("name", "今日歌曲1");
        map2.put("url", "http://www.baidu.com");
        array.add(map2);
        map.put("sub_button", arrayq);
        button.put("button", array);
        menu.put("menu", button);
        System.out.println(JSONUtil.writeObject2Json(menu));
    }

    /**
     * 保存自定义菜单
     */
    @ResponseBody
    @RequiresPermissions(value = {"weixin:wxMenu:add", "weixin:wxMenu:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(WxMenu wxMenu, String menu) throws Exception {
        AjaxJson j = new AjaxJson();
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxMenu.setAccount(account.getAccount());

        List<WxMenu> list = wxMenuService.findList(wxMenu);
        for (WxMenu m : list) {
            wxMenuService.delete(m);
        }
        if (StringUtils.isNotEmpty(menu)) {
            menu = StringEscapeUtils.unescapeHtml3(menu);
            JSONObject menuJson = JSONObject.fromObject(menu);
            JSONArray jsonArray = menuJson.getJSONObject("menu").getJSONArray("button");
            jsonArray = JSONArray.fromObject(jsonArray.toString());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject job = jsonArray.getJSONObject(i);
                WxMenu m = new WxMenu();
                m.setId((i + 1) + "");
                m.setParentId("0");
                m.setAccount(account.getAccount());
                m.setName(job.getString("name"));
                JSONArray array = jsonArray.getJSONObject(i).getJSONArray("sub_button");
                if (array.size() > 0) {
                    for (int k = 0; k < array.size(); k++) {
                        JSONObject job1 = array.getJSONObject(k);
                        WxMenu m1 = new WxMenu();
                        m1.setId(System.currentTimeMillis() + "");
                        m1.setName(job1.getString("name"));
                        m1.setAccount(account.getAccount());
                        if (StringUtils.isNotEmpty(job1.getString("url"))) {
                            m1.setUrl(job1.getString("url"));
                            m1.setType(job1.getString("type"));
                        } else {
                            m1.setInputCode(job1.getString("key"));
                        }
                        m1.setParentId(m.getId());
                        m1.setIsNewRecord(true);
                        wxMenuService.save(m1);
                    }
                } else {
                    if (StringUtils.isNotEmpty(job.getString("url"))) {
                        m.setUrl(job.getString("url"));
                        m.setType(job.getString("type"));
                    } else {
                        m.setInputCode(job.getString("key"));
                    }
                }
                System.out.println(jsonArray);

                m.setIsNewRecord(true);
                wxMenuService.save(m);
                System.out.println(job);
            }
        }
        if (account != null) {
            myService.publishMenu(account);
        }
        j.setSuccess(true);
        j.put("wxMenu", wxMenu);
        j.setMsg("保存并发布成功！");
        return j;
    }

    @ResponseBody
    @RequestMapping(value = "publish")
    public AjaxJson publish() throws WxErrorException {
        AjaxJson j = new AjaxJson();
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        if (account != null) {
            myService.publishMenu(account);
        }
        j.setSuccess(true);
        j.setMsg("同步自定义菜单成功");
        return j;
    }


}