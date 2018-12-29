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
import com.jeeplus.modules.weixin.entity.WxMediaFiles;
import com.jeeplus.modules.weixin.service.WxMediaFilesService;
import com.jeeplus.modules.weixin.service.WxMsgNewsService;
import com.jeeplus.wxapi.process.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.weixin.entity.WxMsgNewsArticle;
import com.jeeplus.modules.weixin.service.WxMsgNewsArticleService;

/**
 * 图文信息Controller
 *
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMsgNewsArticle")
public class WxMsgNewsArticleController extends BaseController {
    @Autowired
    private WxMsgNewsArticleService wxMsgNewsArticleService;

    @ModelAttribute
    public WxMsgNewsArticle get(@RequestParam(required = false) String id) {
        WxMsgNewsArticle entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxMsgNewsArticleService.get(id);
        }
        if (entity == null) {
            entity = new WxMsgNewsArticle();
        }
        return entity;
    }

    /**
     * 图文信息列表页面
     */
    @RequiresPermissions("weixin:wxMsgNews:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxMsgNewsArticle wxMsgNewsArticle, Model model) {
        model.addAttribute("wxMsgNewsArticle", wxMsgNewsArticle);
        return "modules/weixin/news/wxMsgNewsArticleList";
    }

    /**
     * 图文信息列表数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgNews:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(WxMsgNewsArticle wxMsgNewsArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WxMsgNewsArticle> page = wxMsgNewsArticleService.findPage(new Page<WxMsgNewsArticle>(request, response), wxMsgNewsArticle);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑图文信息表单页面
     */
    @RequiresPermissions(value = {"weixin:wxMsgNews:view", "weixin:wxMsgNews:add", "weixin:wxMsgNews:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(WxMsgNewsArticle wxMsgNewsArticle, Model model) {
        model.addAttribute("wxMsgNewsArticle", wxMsgNewsArticle);
        return "modules/weixin/news/wxMsgNewsArticleForm";
    }

    /**
     * 保存图文信息
     */
    @ResponseBody
    @RequiresPermissions(value = {"weixin:wxMsgNews:add", "weixin:wxMsgNews:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(WxMsgNewsArticle wxMsgNewsArticle, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(wxMsgNewsArticle);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        if (wxMsgNewsArticle.getIsNewRecord()) {//编辑表单保存
            WxMsgNewsArticle a = new WxMsgNewsArticle();
            a.setNewsId(wxMsgNewsArticle.getNewsId());
            wxMsgNewsArticle.setNewsIndex(wxMsgNewsArticleService.findListByArticleCount(a) + 1);
        }
        if (StringUtils.isNotEmpty(wxMsgNewsArticle.getContent()))
            wxMsgNewsArticle.setContent(StringEscapeUtils.unescapeHtml3(wxMsgNewsArticle.getContent()));
        wxMsgNewsArticle.setMultType("2");
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxMsgNewsArticle.setAccount(account.getAccount());
        //新增或编辑表单保存
        wxMsgNewsArticleService.save(wxMsgNewsArticle);//保存
//        if (StringUtils.isNotEmpty(wxMsgNewsArticle.getPicDir())) {
//            WxMediaFiles files = new WxMediaFiles();
//            files.setUploadUrl(wxMsgNewsArticle.getPicDir());
//            files = wxMediaFilesService.getFiles(files);
//            if (files == null) {
//                files = new WxMediaFiles();
//            }
//            files.setMediaId(wxMsgNewsArticle.getThumbMediaId());
//            files.setTitle(wxMsgNewsArticle.getTitle());
//            files.setAccount(account.getAccount());
//            files.setMediaId(wxMsgNewsArticle.getThumbMediaId());
//            files.setMediaType(MediaType.Image.toString());
//            files.setIntroduction(wxMsgNewsArticle.getBrief());
//            files.setUploadUrl(wxMsgNewsArticle.getPicDir());
//            wxMediaFilesService.save(files);
//        }
        j.setSuccess(true);
        j.setMsg("保存图文信息成功");
        return j;
    }

    /**
     * 删除图文信息
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgNews:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(WxMsgNewsArticle wxMsgNewsArticle) {
        AjaxJson j = new AjaxJson();
        wxMsgNewsArticleService.delete(wxMsgNewsArticle);
        j.setMsg("删除图文信息成功");
        return j;
    }

    /**
     * 批量删除图文信息
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgNews:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            wxMsgNewsArticleService.delete(wxMsgNewsArticleService.get(id));
        }
        j.setMsg("删除图文信息成功");
        return j;
    }

}