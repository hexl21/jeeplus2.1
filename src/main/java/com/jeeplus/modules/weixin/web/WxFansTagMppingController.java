/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.entity.WxFans;
import com.jeeplus.modules.weixin.entity.WxFansTag;
import com.jeeplus.modules.weixin.entity.WxFansTagMpping;
import com.jeeplus.modules.weixin.service.WxFansService;
import com.jeeplus.modules.weixin.service.WxFansTagMppingService;
import com.jeeplus.modules.weixin.service.WxFansTagService;
import com.jeeplus.wxapi.process.WxApiClient;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 微信用户标签Controller
 *
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxFansTagMpping")
public class WxFansTagMppingController extends BaseController {
    @Autowired
    private WxFansService wxFansService;
    @Autowired
    private WxFansTagMppingService wxFansTagMppingService;
    @Autowired
    private WxFansTagService wxFansTagService;

    @ModelAttribute
    public WxFansTagMpping get(@RequestParam(required = false) String id) {
        WxFansTagMpping entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxFansTagMppingService.get(id);
        }
        if (entity == null) {
            entity = new WxFansTagMpping();
        }
        return entity;
    }

    /**
     * 微信用户标签列表页面
     */
    @RequiresPermissions("weixin:wxFans:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxFansTagMpping wxFansTagMpping, Model model) {
        model.addAttribute("wxFansTagMpping", wxFansTagMpping);
        return "modules/weixin/tag/wxFansTagMppingList";
    }

    @RequiresPermissions(value = {"weixin:wxFans:view", "weixin:wxFans:add", "weixin:wxFans:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(WxFansTagMpping wxFansTagMpping, Model model) {
        List<WxFansTag> list = wxFansTagService.findList(new WxFansTag());
        model.addAttribute("list", list);
        model.addAttribute("wxFansTagMpping", wxFansTagMpping);
        return "modules/weixin/tag/wxFansTagMppingForm";
    }

    /**
     * 保存微信用户标签
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxJson save(WxFansTagMpping wxFansTagMpping) throws Exception {
        AjaxJson j = new AjaxJson();
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        JSONObject jsonObject = null;
        Map<String, String> map = new HashMap<String, String>();
        if (!wxFansTagMpping.getIsNewRecord()) {//编辑表单保存
            WxFansTagMpping t = wxFansTagMppingService.get(wxFansTagMpping.getId());//从数据库取出记录的值
            MyBeanUtils.copyBeanNotNull2Bean(wxFansTagMpping, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
            // wxFansTagMppingService.save(t);//保存
        } else {//新增表单保存
            String opIds[] = wxFansTagMpping.getOpenIds().split(",");
            StringBuilder builder = new StringBuilder();
            for (String s : opIds) {
                if (StringUtils.isNotEmpty(builder)) {
                    builder.append(",");
                }
                builder.append("\"" + s + "\"");
            }
            System.out.println(Arrays.asList(builder).toString());
            if (StringUtils.isNotEmpty(wxFansTagMpping.getTagId())) {
                String tags[] = wxFansTagMpping.getTagId().split(",");
                for (String tag : tags) {
                    map = new HashMap<>();
                    map.put("openid_list", Arrays.asList(builder).toString());
                    map.put("tagid", tag);
                    String st = "{\"openid_list\" :" + Arrays.asList(builder).toString() + ",\"tagid\" : " + tag + "}";
                    jsonObject = WxApiClient.batchtagging(account, st);
                    if (jsonObject.getString("errmsg").equals("ok")) {
                        for (String o : opIds) {
                            wxFansTagMpping.setTagId(tag);
                            wxFansTagMpping.setOpenId(o);
                            WxFansTagMpping mpping = wxFansTagMppingService.getMapping(wxFansTagMpping);
                            if (mpping != null) {
                                continue;
                            }
                            wxFansTagMppingService.save(wxFansTagMpping);//保存
                        }
                        WxFansTag tag1 = wxFansTagService.get(tag);
                        if (tag1 != null) {
                            tag1.setCount(opIds.length);
                            wxFansTagService.save(tag1);
                        }
                        j.setSuccess(true);
                        j.setMsg("处理成功！");

                    } else {
                        j.setSuccess(false);
                        j.setMsg("处理失败！");;
                    }
                }
            }else{
                j.setSuccess(false);
                j.setMsg("请传递参数");;
            }
        }
        return j;
    }

    /**
     * 微信用户标签列表数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxFans:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(WxFansTagMpping wxFansTagMpping, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<WxFansTagMpping> page = wxFansTagMppingService.findPage(new Page<WxFansTagMpping>(request, response), wxFansTagMpping);
        List<WxFansTagMpping> list = Lists.newArrayList();
        for (WxFansTagMpping m : page.getList()) {
            m.setFans(wxFansService.getByOpenId(m.getOpenId()));
            list.add(m);
        }
        page.setList(list);
        return getBootstrapData(page);
    }
}