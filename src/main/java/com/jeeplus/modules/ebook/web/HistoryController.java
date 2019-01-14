/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.ebook.entity.History;
import com.jeeplus.modules.ebook.service.HistoryService;
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
import java.util.List;
import java.util.Map;

/**
 * 管理历史Controller
 *
 * @author 高龙
 * @version 2019-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/ebook/history")
public class HistoryController extends BaseController {

    @Autowired
    private HistoryService historyService;

    @ModelAttribute
    public History get(@RequestParam(required = false) String id) {
        History entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = historyService.get(id);
        }
        if (entity == null) {
            entity = new History();
        }
        return entity;
    }

    /**
     * 管理历史列表页面
     */
    @RequiresPermissions("ebook:history:list")
    @RequestMapping(value = {"list", ""})
    public String list(History history, Model model) {
        model.addAttribute("history", history);
        return "modules/ebook/historyList";
    }

    /**
     * 管理历史列表数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:history:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(History history, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<History> page = historyService.findPage(new Page<History>(request, response), history);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑管理历史表单页面
     */
    @RequiresPermissions(value = {"ebook:history:view", "ebook:history:add", "ebook:history:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(History history, Model model) {
        model.addAttribute("history", history);
        return "modules/ebook/historyForm";
    }

    /**
     * 保存管理历史
     */
    @ResponseBody
    @RequiresPermissions(value = {"ebook:history:add", "ebook:history:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(History history, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(history);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        historyService.save(history);//保存
        j.setSuccess(true);
        j.setMsg("保存管理历史成功");
        return j;
    }

    /**
     * 删除管理历史
     */
    @ResponseBody
    @RequiresPermissions("ebook:history:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(History history) {
        AjaxJson j = new AjaxJson();
        historyService.delete(history);
        j.setMsg("删除管理历史成功");
        return j;
    }

    /**
     * 批量删除管理历史
     */
    @ResponseBody
    @RequiresPermissions("ebook:history:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            historyService.delete(historyService.get(id));
        }
        j.setMsg("删除管理历史成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("ebook:history:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(History history, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "管理历史" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<History> page = historyService.findPage(new Page<History>(request, response, -1), history);
            new ExportExcel("管理历史", History.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出管理历史记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:history:import")
    @RequestMapping(value = "import")
    public AjaxJson importFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<History> list = ei.getDataList(History.class);
            for (History history : list) {
                try {
                    historyService.save(history);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条管理历史记录。");
            }
            j.setMsg("已成功导入 " + successNum + " 条管理历史记录" + failureMsg);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入管理历史失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 下载导入管理历史数据模板
     */
    @ResponseBody
    @RequiresPermissions("ebook:history:import")
    @RequestMapping(value = "import/template")
    public AjaxJson importFileTemplate(HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "管理历史数据导入模板.xlsx";
            List<History> list = Lists.newArrayList();
            new ExportExcel("管理历史数据", History.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入模板下载失败！失败信息：" + e.getMessage());
        }
        return j;
    }

}