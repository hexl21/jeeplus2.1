/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.entity.WxMediaFiles;
import com.jeeplus.modules.weixin.entity.WxMsgNewsArticle;
import com.jeeplus.modules.weixin.service.WxMediaFilesService;
import com.jeeplus.modules.weixin.service.WxMsgNewsArticleService;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.MediaType;
import com.jeeplus.wxapi.process.WxApi;
import com.jeeplus.wxapi.process.WxApiClient;
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
import com.jeeplus.modules.weixin.entity.WxMsgNews;
import com.jeeplus.modules.weixin.service.WxMsgNewsService;

/**
 * 图文信息Controller
 *
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMsgNews")
public class WxMsgNewsController extends BaseController {
    @Autowired
    private WxMsgNewsArticleService wxMsgNewsArticleService;
    @Autowired
    private WxMsgNewsService wxMsgNewsService;
    @Autowired
    private WxMediaFilesService wxMediaFilesService;

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
    @RequiresPermissions("weixin:wxMsgNews:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxMsgNews wxMsgNews, Model model) {
        model.addAttribute("wxMsgNews", wxMsgNews);
        return "modules/weixin/news/wxMsgNewsList";
    }

    /**
     * 图文信息列表数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgNews:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(WxMsgNews wxMsgNews, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WxMsgNews> page = wxMsgNewsService.findPage(new Page<WxMsgNews>(request, response), wxMsgNews);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑图文信息表单页面
     */
    @RequiresPermissions(value = {"weixin:wxMsgNews:view", "weixin:wxMsgNews:add", "weixin:wxMsgNews:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(WxMsgNews wxMsgNews, Model model) {
        model.addAttribute("wxMsgNews", wxMsgNews);
        return "modules/weixin/news/wxMsgNewsForm";
    }

    /**
     * 保存图文信息
     */
    @ResponseBody
    @RequiresPermissions(value = {"weixin:wxMsgNews:add", "weixin:wxMsgNews:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(WxMsgNews wxMsgNews, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(wxMsgNews);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }

        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxMsgNews.setStatus("0");
        if (StringUtils.isNotEmpty(wxMsgNews.getContent()))
            wxMsgNews.setContent(StringEscapeUtils.unescapeHtml3(wxMsgNews.getContent()));
        WxMsgNewsArticle article = new WxMsgNewsArticle();
        if (!wxMsgNews.getIsNewRecord()) {//编辑表单保存
            article.setNewsId(wxMsgNews.getId());
            article.setNewsIndex(1L);
            article = wxMsgNewsArticleService.getArticle(article);
            if (article == null) {
                article = new WxMsgNewsArticle();
            }
        }
        wxMsgNews.setAccount(account.getAccount());
        wxMsgNewsService.save(wxMsgNews);//保存


        article.setNewsIndex(1L);
        article.setMultType(wxMsgNews.getMultType());
        article.setMediaId(wxMsgNews.getMediaId());
        article.setAccount(wxMsgNews.getAccount());
        article.setAuthor(wxMsgNews.getAuthor());
        article.setBrief(wxMsgNews.getBrief());
        article.setFromUrl(wxMsgNews.getFromUrl());
        article.setNewsIndex(1L);
        article.setTitle(wxMsgNews.getTitle());
        article.setThumbMediaId(wxMsgNews.getThumbMediaId());
        article.setContent(wxMsgNews.getContent());
        article.setPicDir(wxMsgNews.getPicDir());
        article.setPicPath(wxMsgNews.getPicPath());
        article.setShowPic(wxMsgNews.getShowPic());
        article.setUrl(wxMsgNews.getUrl());
        article.setNewsId(wxMsgNews.getId());
        wxMsgNewsArticleService.save(article);


        j.setSuccess(true);
        j.setMsg("保存图文信息成功");
        return j;
    }

    @ResponseBody
    @RequestMapping(value = "updateStatus")
    public AjaxJson updateStatus(WxMsgNews wxMsgNews) {
        AjaxJson j = new AjaxJson();
        try {
            WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
            String content = wxMsgNews.getContent();
            if (wxMsgNews.getStatus().equals("1")) {

                String description = wxMsgNews.getContent();
                description = description.replaceAll("'", "\"");
                String subFilePath = "";
                String subOldFilePath = "";
                if (description.contains("img")) {
                    Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                    Matcher m = p.matcher(description);
                    while (m.find()) {
                        String imgSrc = m.group(1);
                        subOldFilePath += imgSrc + ",";
                        subFilePath += Global.getUserfilesBaseDir() + imgSrc + ",";
                    }
                }
                if (StringUtils.isNotBlank(subFilePath)) {
                    subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
                    subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);
                    // 本地图片地址
                    String[] imgPathArry = subFilePath.split(",");
                    String[] imgOldPathArry = subOldFilePath.split(",");

                    String[] newPathArry = new String[imgPathArry.length];
                    for (int i = 0; i < imgPathArry.length; i++) {
                        String newFilePath = imgPathArry[i];
                        JSONObject imgResultObj = WxApiClient.uploadMaterialImg(newFilePath, account);
                        String contentContentUrl = "";
                        if (imgResultObj != null && imgResultObj.containsKey("url")) {
                            contentContentUrl = imgResultObj.getString("url");
                        }
                        newPathArry[i] = contentContentUrl;
                    }
                    for (int i = 0; i < imgPathArry.length; i++) {
                        description = description.replace(imgOldPathArry[i], newPathArry[i]);
                    }
                }
                wxMsgNews.setContent(description);
                String accessToken = WxApiClient.getAccessToken(account);
                String materialType = MediaType.Thumb.toString();
                Map<String, String> params = new HashMap<>();
                params.put("title", wxMsgNews.getTitle());

                List<WxMsgNews> msgNewsList = new ArrayList<WxMsgNews>();
                msgNewsList.add(wxMsgNews);
                JSONObject resultObj = null;
                JSONObject object = WxApi.addMateria(accessToken, materialType, Global.getUserfilesBaseDir() + wxMsgNews.getPicDir(), params);
                wxMsgNews.setThumbMediaId(object.getString("media_id"));
                String imgMediaId = wxMsgNews.getThumbMediaId();
                WxMsgNewsArticle article1 = new WxMsgNewsArticle();
                article1.setNewsId(wxMsgNews.getId());
                List<WxMsgNewsArticle> articleList = wxMsgNewsArticleService.findList(article1);
                for (WxMsgNewsArticle a : articleList) {
                    a.setStatus("1");
                    if (a.getNewsIndex() == 1) {
                        a.setThumbMediaId(object.getString("media_id"));
                        a.setPicPath(object.getString("url"));
                    } else {
                        params = new HashMap<>();
                        params.put("title", a.getTitle());
                        object = WxApi.addMateria(accessToken, materialType, Global.getUserfilesBaseDir() + a.getPicDir(), params);
                        a.setThumbMediaId(object.getString("media_id"));
                        a.setPicPath(object.getString("url"));
                    }
                    wxMsgNewsArticleService.save(a);
                }
                wxMsgNewsService.save(wxMsgNews);

                if (wxMsgNews.getMultType().equals("2")) {
                    resultObj = WxApiClient.addMoreNewsMaterial(wxMsgNews, account);
                    if (resultObj != null && resultObj.containsKey("media_id")) {
                        String newsMediaId = resultObj.getString("media_id");
                        wxMsgNews.setMediaId(newsMediaId);
                        JSONObject newsResult = WxApiClient.getMaterial(newsMediaId, account);
                        JSONArray articles = newsResult.getJSONArray("news_item");
                        JSONObject article = (JSONObject) articles.get(0);
                        wxMsgNews.setUrl(article.getString("url"));
                        wxMsgNews.setPicPath(article.getString("thumb_url"));
                        articleList = wxMsgNews.getArticleList();
                        int i = 0;
                        for (WxMsgNewsArticle a : articleList) {
                            a.setStatus("1");
                            a.setUrl(((JSONObject) articles.get(i)).getString("url"));
                            a.setPicPath(((JSONObject) articles.get(i)).getString("thumb_url"));
                            a.setMediaId(newsMediaId);
                            wxMsgNewsArticleService.save(a);
                            i++;
                        }
                    }
                } else {
                    resultObj = WxApiClient.addNewsMaterial(msgNewsList, imgMediaId, account);
                    wxMsgNews.setPicPath(object.getString("url"));
                    wxMsgNews.setMediaId(resultObj.getString("media_id"));
                }
                if (StringUtils.isNotEmpty(wxMsgNews.getPicDir())) {
                    WxMediaFiles files = new WxMediaFiles();
                    files.setUploadUrl(wxMsgNews.getPicDir());
                    files = wxMediaFilesService.getFiles(files);
                    if (files == null) {
                        files = new WxMediaFiles();
                    }
                    files.setTitle(wxMsgNews.getTitle());
                    files.setAccount(account.getAccount());
                    files.setMediaId(wxMsgNews.getThumbMediaId());
                    files.setMediaType(MediaType.Image.toString());
                    files.setIntroduction(wxMsgNews.getBrief());
                    files.setUploadUrl(wxMsgNews.getPicDir());
                    wxMediaFilesService.save(files);
                }

            } else {
                if (StringUtils.isNotEmpty(wxMsgNews.getMediaId()))
                    WxApiClient.deleteMaterial(wxMsgNews.getMediaId(), account);

            }
            wxMsgNews.setContent(content);
            wxMsgNewsService.save(wxMsgNews);
            j.setMsg("处理成功！");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("处理失败！");
        }
        return j;
    }

    /**
     * 删除图文信息
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgNews:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(WxMsgNews wxMsgNews) throws WxErrorException {
        AjaxJson j = new AjaxJson();
        if (wxMsgNews.getStatus().equals("1")) {
            WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
            if (StringUtils.isNotEmpty(wxMsgNews.getMediaId()))
                WxApiClient.deleteMaterial(wxMsgNews.getMediaId(), account);
        }
        wxMsgNewsService.delete(wxMsgNews);
        j.setMsg("删除图文信息成功");
        return j;
    }

    /**
     * 批量删除图文信息
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMsgNews:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) throws WxErrorException {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            WxMsgNews wxMsgNews = wxMsgNewsService.get(id);
            if (wxMsgNews.getStatus().equals("1")) {
                WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
                if (StringUtils.isNotEmpty(wxMsgNews.getMediaId()))
                    WxApiClient.deleteMaterial(wxMsgNews.getMediaId(), account);
            }
            wxMsgNewsService.delete(wxMsgNews);
        }
        j.setMsg("删除图文信息成功");
        return j;
    }
}