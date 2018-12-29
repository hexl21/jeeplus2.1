/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.CacheUtils;
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
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.modules.weixin.service.WxAccountService;

/**
 * 公众号Controller
 *
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxAccount")
public class WxAccountController extends BaseController {

    @Autowired
    private WxAccountService wxAccountService;

    @ModelAttribute
    public WxAccount get(@RequestParam(required = false) String id) {
        WxAccount entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxAccountService.get(id);
        }
        if (entity == null) {
            entity = new WxAccount();
        }
        return entity;
    }

    /**
     * 公众号列表页面
     */
    @RequiresPermissions("weixin:wxAccount:list")
    @RequestMapping(value = {"list", ""})
    public String list(WxAccount wxAccount, Model model) {
        model.addAttribute("wxAccount", wxAccount);
        return "modules/weixin/account/wxAccountList";
    }

    /**
     * 公众号列表数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxAccount:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response), wxAccount);
        if (page.getList().size() > 0) {
            CacheUtils.put("WxAccount", page.getList().get(0));
        }
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑公众号表单页面
     */
    @RequiresPermissions(value = {"weixin:wxAccount:view", "weixin:wxAccount:add", "weixin:wxAccount:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(WxAccount wxAccount, Model model) {
        model.addAttribute("wxAccount", wxAccount);
        return "modules/weixin/account/wxAccountForm";
    }

    /**
     * 保存公众号
     */
    @ResponseBody
    @RequiresPermissions(value = {"weixin:wxAccount:add", "weixin:wxAccount:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(WxAccount wxAccount, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String url = "/wxapi/" + wxAccount.getAccount() + "/message.html";
        wxAccount.setUrl(url);
        String errMsg = beanValidator(wxAccount);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        if (wxAccount.getIsNewRecord()) {
            wxAccount.setToken(UUID.randomUUID().toString().replace("-", ""));
        }
        wxAccountService.save(wxAccount);//保存
        j.setSuccess(true);
        j.setMsg("保存公众号成功");
        return j;
    }

    @ResponseBody
    @RequestMapping(value = "status")
    public AjaxJson status(WxAccount wxAccount) {
        AjaxJson j = new AjaxJson();
        List<WxAccount> list = wxAccountService.findList(new WxAccount());
        for (WxAccount a : list) {
            if (a.getId().equals(wxAccount.getId())) {
                a.setIsDefault("1");
                CacheUtils.put("WxAccount", a);
            } else {
                a.setIsDefault("0");
            }
            wxAccountService.updateDefault(a);
        }

        j.setMsg("设置成功！");
        return j;
    }

    /**
     * 删除公众号
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxAccount:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(WxAccount wxAccount) {
        AjaxJson j = new AjaxJson();
        wxAccountService.delete(wxAccount);
        j.setMsg("删除公众号成功");
        return j;
    }


    /**
     * 批量删除公众号
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxAccount:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            wxAccountService.delete(wxAccountService.get(id));
        }
        j.setMsg("删除公众号成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxAccount:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(WxAccount wxAccount, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "公众号" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<WxAccount> page = wxAccountService.findPage(new Page<WxAccount>(request, response, -1), wxAccount);
            new ExportExcel("公众号", WxAccount.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出公众号记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxAccount:import")
    @RequestMapping(value = "import")
    public AjaxJson importFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<WxAccount> list = ei.getDataList(WxAccount.class);
            for (WxAccount wxAccount : list) {
                try {
                    wxAccountService.save(wxAccount);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条公众号记录。");
            }
            j.setMsg("已成功导入 " + successNum + " 条公众号记录" + failureMsg);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入公众号失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 下载导入公众号数据模板
     */
    @ResponseBody
    @RequiresPermissions("weixin:wxAccount:import")
    @RequestMapping(value = "import/template")
    public AjaxJson importFileTemplate(HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "公众号数据导入模板.xlsx";
            List<WxAccount> list = Lists.newArrayList();
            new ExportExcel("公众号数据", WxAccount.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入模板下载失败！失败信息：" + e.getMessage());
        }
        return j;
    }

}