/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.process.HttpMethod;
import com.jeeplus.wxapi.process.WxApi;
import com.jeeplus.wxapi.process.WxApiClient;
import com.jeeplus.wxapi.util.JSONUtil;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import com.jeeplus.modules.weixin.entity.WxFansTag;
import com.jeeplus.modules.weixin.service.WxFansTagService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户标签Controller
 *
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxFansTag")
public class WxFansTagController extends BaseController {

    @Autowired
    private WxFansTagService wxFansTagService;

    @ModelAttribute
    public WxFansTag get(@RequestParam(required = false) String id) {
        WxFansTag entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxFansTagService.get(id);
        }
        if (entity == null) {
            entity = new WxFansTag();
        }
        return entity;
    }

    /**
     * 用户标签列表页面
     */
    @RequiresPermissions("weixin:wxFansTag:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxFansTag wxFansTag, Model model) {
        model.addAttribute("wxFansTag", wxFansTag);
        return "modules/weixin/tag/wxFansTagList";
    }

    /**
     * 用户标签列表数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxFansTag:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(WxFansTag wxFansTag, HttpServletRequest request, HttpServletResponse response, Model model) {

        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        wxFansTag.setAccount(account.getAccount());
        Page<WxFansTag> page = wxFansTagService.findPage(new Page<WxFansTag>(request, response), wxFansTag);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑用户标签表单页面
     */
    @RequiresPermissions(value = {"weixin:wxFansTag:view", "weixin:wxFansTag:add", "weixin:wxFansTag:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(WxFansTag wxFansTag, Model model) {
        model.addAttribute("wxFansTag", wxFansTag);
        return "modules/weixin/tag/wxFansTagForm";
    }

    /**
     * 保存用户标签
     */
    @ResponseBody
    @RequiresPermissions(value = {"weixin:wxFansTag:add", "weixin:wxFansTag:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(WxFansTag wxFansTag, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(wxFansTag);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        WxFansTag tag = wxFansTagService.getName(wxFansTag);
        if (tag != null) {
            j.setSuccess(false);
            j.setMsg("用户标签已存在");
            return j;
        }
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        JSONObject jsonObject = null;
        Map<String, JSONObject> map = new HashMap<String, JSONObject>();
        JSONObject params = new JSONObject();
        params.put("name", wxFansTag.getName());
        map.put("tag", params);
        try {
            if (!wxFansTag.getIsNewRecord()) {//编辑表单保存
                params.put("id", wxFansTag.getId());
                jsonObject = WxApiClient.update_tags(account, JSONUtil.writeObject2Json(map));
                if (jsonObject.getString("errmsg").equals("ok")) {
                    wxFansTagService.save(wxFansTag);
                }
            } else {
                jsonObject = WxApiClient.create_tags(account, JSONUtil.writeObject2Json(map));
                if (jsonObject.containsKey("tag")) {
                    String json = jsonObject.getString("tag");
                    Object obj = JSON.parseObject(json);
                    if (obj != null) {
                        wxFansTag.setIsNewRecord(true);
                        wxFansTag.setId(((JSONObject) obj).getString("id"));
                        wxFansTag.setCount(0);
                        wxFansTagService.save(wxFansTag);
                    }
                }
            }
            j.setSuccess(true);
            j.setMsg("保存用户标签成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("保存用户标签失败");
        }
        return j;
    }

    /**
     * 删除用户标签
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxFansTag:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(WxFansTag wxFansTag) {
        AjaxJson j = new AjaxJson();
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        Map<String, JSONObject> map = new HashMap<String, JSONObject>();
        JSONObject params = new JSONObject();
        params.put("id", wxFansTag.getId());
        map.put("tag", params);
        try {
            JSONObject jsonObject = WxApiClient.delete_tags(account, JSONUtil.writeObject2Json(map));
            if (jsonObject.getString("errmsg").equals("ok")) {
                wxFansTagService.delete(wxFansTag);
                j.setMsg("删除用户标签成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("删除失败");
        }
        return j;
    }

    /**
     * 批量删除用户标签
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxFansTag:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        Map<String, JSONObject> map = new HashMap<String, JSONObject>();

        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        try {
            for (String id : idArray) {
                JSONObject params = new JSONObject();
                params.put("id", id);
                map.put("tag", params);
                JSONObject jsonObject = WxApiClient.delete_tags(account, JSONUtil.writeObject2Json(map));
                if (jsonObject.getString("errmsg").equals("ok")) {
                    wxFansTagService.delete(wxFansTagService.get(id));
                }
            }
            j.setMsg("删除用户标签成功");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("删除失败");
        }
        return j;
    }

    @ResponseBody
    @RequestMapping(value = "synFansTagList")
    public AjaxJson synFansTagList() throws WxErrorException {
        AjaxJson j = new AjaxJson();
        WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
        doSyncAccountFansTagList(account);
        j.setMsg("批量同步用户标签成功");
        return j;
    }

    //同步标签
    private int doSyncAccountFansTagList(WxAccount wxAccount) throws WxErrorException {
        String url = WxApi.get_tags(WxApiClient.getAccessToken(wxAccount));
        JSONObject jsonObject = WxApi.httpsRequest(url, HttpMethod.GET, null);
        int count = 0;
        if (jsonObject.containsKey("errcode")) {
            return count;
        }
        if (jsonObject.containsKey("tags")) {
            List<WxFansTag> list = (List) JSONUtil.json2ObjectList(jsonObject.getString("tags"), WxFansTag.class);
            for (WxFansTag t : list) {
                WxFansTag tag = wxFansTagService.get(t.getId());
                if (tag != null)
                    continue;
                t.setIsNewRecord(true);
                t.setAccount(wxAccount.getAccount());
                wxFansTagService.save(t);
                count++;
            }
        }
        return count;
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "bootstrapTreeData")
    public List<Map<String, Object>> bootstrapTreeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<WxFansTag> list = wxFansTagService.findList(new WxFansTag());
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 0);
        map.put("pId", 0);
        map.put("name", "全部用户");
        mapList.add(map);
        for (int i = 0; i < list.size(); i++) {
            WxFansTag e = list.get(i);
            if (extId == null || (extId != null && !extId.equals(e.getId()))) {
                map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", 0);
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }
}