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
import com.jeeplus.modules.ebook.entity.Enshrine;
import com.jeeplus.modules.ebook.service.EnshrineService;
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
 * 管理收藏Controller
 * @author 高龙
 * @version 2019-01-16
 */
@Controller
@RequestMapping(value = "${adminPath}/ebook/enshrine")
public class EnshrineController extends BaseController {

    @Autowired
    private EnshrineService enshrineService;

    @ModelAttribute
    public Enshrine get(@RequestParam(required = false) String id) {
        Enshrine entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = enshrineService.get(id);
        }
        if (entity == null) {
            entity = new Enshrine();
        }
        return entity;
    }

    /**
     * 管理收藏列表页面
     */
    @RequiresPermissions("ebook:enshrine:list")
    @RequestMapping(value = {"list", ""})
    public String list(Enshrine enshrine, Model model) {
        model.addAttribute("enshrine", enshrine);
        return "modules/ebook/enshrineList";
    }

    /**
     * 管理收藏列表数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:enshrine:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Enshrine enshrine, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Enshrine> page = enshrineService.findPage(new Page<Enshrine>(request, response), enshrine);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑管理收藏表单页面
     */
    @RequiresPermissions(value = {"ebook:enshrine:view", "ebook:enshrine:add", "ebook:enshrine:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Enshrine enshrine, Model model) {
        model.addAttribute("enshrine", enshrine);
        return "modules/ebook/enshrineForm";
    }

    /**
     * 保存管理收藏
     */
    @ResponseBody
    @RequiresPermissions(value = {"ebook:enshrine:add", "ebook:enshrine:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(Enshrine enshrine, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(enshrine);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }
        //新增或编辑表单保存
        enshrineService.save(enshrine);//保存
        j.setSuccess(true);
        j.setMsg("保存管理收藏成功");
        return j;
    }

    /**
     * 删除管理收藏
     */
    @ResponseBody
    @RequiresPermissions("ebook:enshrine:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(Enshrine enshrine) {
        AjaxJson j = new AjaxJson();
        enshrineService.delete(enshrine);
        j.setMsg("删除管理收藏成功");
        return j;
    }

    /**
     * 批量删除管理收藏
     */
    @ResponseBody
    @RequiresPermissions("ebook:enshrine:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            enshrineService.delete(enshrineService.get(id));
        }
        j.setMsg("删除管理收藏成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("ebook:enshrine:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Enshrine enshrine, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "管理收藏" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Enshrine> page = enshrineService.findPage(new Page<Enshrine>(request, response, -1), enshrine);
            new ExportExcel("管理收藏", Enshrine.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出管理收藏记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @ResponseBody
    @RequiresPermissions("ebook:enshrine:import")
    @RequestMapping(value = "import")
    public AjaxJson importFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Enshrine> list = ei.getDataList(Enshrine.class);
            for (Enshrine enshrine : list) {
                try {
                    enshrineService.save(enshrine);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条管理收藏记录。");
            }
            j.setMsg("已成功导入 " + successNum + " 条管理收藏记录" + failureMsg);
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导入管理收藏失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 下载导入管理收藏数据模板
     */
    @ResponseBody
	@RequiresPermissions("ebook:enshrine:import")
    @RequestMapping(value = "import/template")
    public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "管理收藏数据导入模板.xlsx";
            List<Enshrine> list = Lists.newArrayList();
            new ExportExcel("管理收藏数据", Enshrine.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}