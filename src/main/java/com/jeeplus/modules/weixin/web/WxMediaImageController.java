/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.entity.WxMediaFiles;
import com.jeeplus.modules.weixin.service.WxMediaFilesService;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.MediaType;
import com.jeeplus.wxapi.process.WxApi;
import com.jeeplus.wxapi.process.WxApiClient;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 图片素材Controller
 *
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMediaImage")
public class WxMediaImageController extends BaseController {

    @Autowired
    private WxMediaFilesService wxMediaFilesService;

    @ModelAttribute
    public WxMediaFiles get(@RequestParam(required = false) String id) {
        WxMediaFiles entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxMediaFilesService.get(id);
        }
        if (entity == null) {
            entity = new WxMediaFiles();
        }
        return entity;
    }

    /**
     * 图片素材列表页面
     */
    @RequiresPermissions("weixin:wxMediaFiles:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxMediaFiles wxMediaFiles, Model model) {
        model.addAttribute("wxMediaFiles", wxMediaFiles);
        return "modules/weixin/image/wxMediaFilesList";
    }

    @RequestMapping(value = "select")
    public String select(WxMediaFiles wxMediaFiles, Model model) {
        model.addAttribute("wxMediaFiles", wxMediaFiles);
        return "modules/weixin/select/imageSelect";
    }

    /**
     * 图片素材列表数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMediaFiles:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(WxMediaFiles wxMediaFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxMediaFiles.setAccount(account.getAccount());
        wxMediaFiles.setMediaType(MediaType.Image.toString());
        Page<WxMediaFiles> page = wxMediaFilesService.findPage(new Page<WxMediaFiles>(request, response), wxMediaFiles);
        return getBootstrapData(page);
    }

    @ResponseBody
    @RequestMapping(value = "dataSelect")
    public Map<String, Object> dataSelect(WxMediaFiles wxMediaFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxMediaFiles.setAccount(account.getAccount());
        wxMediaFiles.setMediaType(MediaType.Image.toString());
        Page<WxMediaFiles> page = wxMediaFilesService.findPage(new Page<WxMediaFiles>(request, response), wxMediaFiles);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑图片素材表单页面
     */
    @RequiresPermissions(value = {"weixin:wxMediaFiles:view", "weixin:wxMediaFiles:add", "weixin:wxMediaFiles:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(WxMediaFiles wxMediaFiles, Model model) {
        model.addAttribute("wxMediaFiles", wxMediaFiles);
        return "modules/weixin/image/wxMediaFilesForm";
    }

    /**
     * 保存图片素材
     */
    @ResponseBody
    @RequiresPermissions(value = {"weixin:wxMediaFiles:add", "weixin:wxMediaFiles:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(WxMediaFiles wxMediaFiles, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(wxMediaFiles);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        if (StringUtils.isBlank(wxMediaFiles.getMediaId())) {//如果ID是空为添加
            WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
            String accessToken = WxApiClient.getAccessToken(account);
            String materialType = MediaType.Image.toString();
            JSONObject object = WxApi.uploadMediaFiles(accessToken, new File(wxMediaFiles.getFilesPath()), wxMediaFiles.getTitle(), wxMediaFiles.getIntroduction(), materialType);
            wxMediaFiles.setMediaId(object.getString("media_id"));
            wxMediaFiles.setMediaType(materialType);
            wxMediaFiles.setAccount(account.getAccount());
        }

        wxMediaFilesService.save(wxMediaFiles);//保存
        j.setSuccess(true);
        j.setMsg("保存图片素材成功");
        return j;
    }

    /**
     * 删除图片素材
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMediaFiles:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(WxMediaFiles wxMediaFiles) throws WxErrorException {
        AjaxJson j = new AjaxJson();
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        WxApiClient.deleteMaterial(wxMediaFiles.getMediaId(), account);
        wxMediaFilesService.delete(wxMediaFiles);
        j.setMsg("删除图片素材成功");
        return j;
    }

    /**
     * 批量删除图片素材
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxMediaFiles:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) throws WxErrorException {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        for (String id : idArray) {
            WxApiClient.deleteMaterial(wxMediaFilesService.get(id).getMediaId(), account);
            wxMediaFilesService.delete(wxMediaFilesService.get(id));
        }
        j.setMsg("删除图片素材成功");
        return j;
    }
}