/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.weixin.entity.WxAccount;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.weixin.entity.WxMsgBase;
import com.jeeplus.modules.weixin.service.WxMsgBaseService;

/**
 * 微信消息Controller
 *
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMsgBase")
public class WxMsgBaseController extends BaseController {

    @Autowired
    private WxMsgBaseService wxMsgBaseService;

    @ModelAttribute
    public WxMsgBase get(@RequestParam(required = false) String id) {
        WxMsgBase entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxMsgBaseService.get(id);
        }
        if (entity == null) {
            entity = new WxMsgBase();
        }
        return entity;
    }

    /**
     * 微信消息列表页面
     */
    @RequiresPermissions("weixin:wxMsgBase:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxMsgBase wxMsgBase, Model model) {
        model.addAttribute("wxMsgBase", wxMsgBase);
        return "modules/weixin/msg/wxMsgBaseList";
    }

    /**
     * 微信消息列表数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgBase:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(WxMsgBase wxMsgBase, HttpServletRequest request, HttpServletResponse response, Model model) {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxMsgBase.setAccount(account.getAccount());
        Page<WxMsgBase> page = wxMsgBaseService.findPage(new Page<WxMsgBase>(request, response), wxMsgBase);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑微信消息表单页面
     */
    @RequiresPermissions(value = {"weixin:wxMsgBase:view", "weixin:wxMsgBase:add", "weixin:wxMsgBase:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(WxMsgBase wxMsgBase, Model model) {
        model.addAttribute("wxMsgBase", wxMsgBase);
        return "modules/weixin/msg/wxMsgBaseForm";
    }

    /**
     * 保存微信消息
     */
    @ResponseBody
    @RequiresPermissions(value = {"weixin:wxMsgBase:add", "weixin:wxMsgBase:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(WxMsgBase wxMsgBase, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(wxMsgBase);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        wxMsgBaseService.save(wxMsgBase);//保存
        j.setSuccess(true);
        j.setMsg("保存微信消息成功");
        return j;
    }

    /**
     * 删除微信消息
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgBase:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(WxMsgBase wxMsgBase) {
        AjaxJson j = new AjaxJson();
        wxMsgBaseService.delete(wxMsgBase);
        j.setMsg("删除微信消息成功");
        return j;
    }
}