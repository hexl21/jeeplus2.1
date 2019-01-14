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
import com.jeeplus.modules.ebook.entity.Chapter;
import com.jeeplus.modules.ebook.service.ChapterService;
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
 * 管理章节Controller
 *
 * @author 高龙
 * @version 2019-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ebook/chapter")
public class ChapterController extends BaseController {

    @Autowired
    private ChapterService chapterService;

    @ModelAttribute
    public Chapter get(@RequestParam(required = false) String id) {
        Chapter entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = chapterService.get(id);
        }
        if (entity == null) {
            entity = new Chapter();
        }
        return entity;
    }

    /**
     * 管理章节列表页面
     */
    @RequiresPermissions("ebook:chapter:list")
    @RequestMapping(value = {"list", ""})
    public String list(Chapter chapter, Model model) {
        model.addAttribute("chapter", chapter);
        return "modules/ebook/chapterList";
    }

    /**
     * 管理章节列表数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:chapter:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Chapter chapter, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Chapter> page = chapterService.findPage(new Page<Chapter>(request, response), chapter);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑管理章节表单页面
     */
    @RequiresPermissions(value = {"ebook:chapter:view", "ebook:chapter:add", "ebook:chapter:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Chapter chapter, Model model) {
        model.addAttribute("chapter", chapter);
        return "modules/ebook/chapterForm";
    }

    /**
     * 保存管理章节
     */
    @ResponseBody
    @RequiresPermissions(value = {"ebook:chapter:add", "ebook:chapter:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(Chapter chapter, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(chapter);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        chapterService.save(chapter);//保存
        j.setSuccess(true);
        j.setMsg("保存管理章节成功");
        return j;
    }

    /**
     * 删除管理章节
     */
    @ResponseBody
    @RequiresPermissions("ebook:chapter:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(Chapter chapter) {
        AjaxJson j = new AjaxJson();
        chapterService.delete(chapter);
        j.setMsg("删除管理章节成功");
        return j;
    }

    /**
     * 批量删除管理章节
     */
    @ResponseBody
    @RequiresPermissions("ebook:chapter:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            chapterService.delete(chapterService.get(id));
        }
        j.setMsg("删除管理章节成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("ebook:chapter:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Chapter chapter, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "管理章节" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<Chapter> page = chapterService.findPage(new Page<Chapter>(request, response, -1), chapter);
            new ExportExcel("管理章节", Chapter.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出管理章节记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:chapter:import")
    @RequestMapping(value = "import")
    public AjaxJson importFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Chapter> list = ei.getDataList(Chapter.class);
            for (Chapter chapter : list) {
                try {
                    chapterService.save(chapter);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条管理章节记录。");
            }
            j.setMsg("已成功导入 " + successNum + " 条管理章节记录" + failureMsg);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入管理章节失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 下载导入管理章节数据模板
     */
    @ResponseBody
    @RequiresPermissions("ebook:chapter:import")
    @RequestMapping(value = "import/template")
    public AjaxJson importFileTemplate(HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "管理章节数据导入模板.xlsx";
            List<Chapter> list = Lists.newArrayList();
            new ExportExcel("管理章节数据", Chapter.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入模板下载失败！失败信息：" + e.getMessage());
        }
        return j;
    }

}