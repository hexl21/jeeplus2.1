/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cms.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.cms.entity.Sensitive;
import com.jeeplus.modules.cms.service.SensitiveService;

/**
 * 敏感词Controller
 * @author toteny
 * @version 2018-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/sensitive")
public class SensitiveController extends BaseController {

	@Autowired
	private SensitiveService sensitiveService;
	
	@ModelAttribute
	public Sensitive get(@RequestParam(required=false) String id) {
		Sensitive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sensitiveService.get(id);
		}
		if (entity == null){
			entity = new Sensitive();
		}
		return entity;
	}
	
	/**
	 * 敏感词列表页面
	 */
	@RequiresPermissions("cms:sensitive:list")
	@RequestMapping(value = {"list", ""})
	public String list(Sensitive sensitive, Model model) {
		model.addAttribute("sensitive", sensitive);
		return "modules/cms/sensitive/sensitiveList";
	}
	
		/**
	 * 敏感词列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:sensitive:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Sensitive sensitive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Sensitive> page = sensitiveService.findPage(new Page<Sensitive>(request, response), sensitive); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑敏感词表单页面
	 */
	@RequiresPermissions(value={"cms:sensitive:view","cms:sensitive:add","cms:sensitive:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Sensitive sensitive, Model model) {
		model.addAttribute("sensitive", sensitive);
		return "modules/cms/sensitive/sensitiveForm";
	}

	/**
	 * 保存敏感词
	 */
	@ResponseBody
	@RequiresPermissions(value={"cms:sensitive:add","cms:sensitive:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Sensitive sensitive, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(sensitive);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		sensitiveService.save(sensitive);//保存
		j.setSuccess(true);
		j.setMsg("保存敏感词成功");
		return j;
	}
	
	/**
	 * 删除敏感词
	 */
	@ResponseBody
	@RequiresPermissions("cms:sensitive:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Sensitive sensitive) {
		AjaxJson j = new AjaxJson();
		sensitiveService.delete(sensitive);
		j.setMsg("删除敏感词成功");
		return j;
	}
	
	/**
	 * 批量删除敏感词
	 */
	@ResponseBody
	@RequiresPermissions("cms:sensitive:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sensitiveService.delete(sensitiveService.get(id));
		}
		j.setMsg("删除敏感词成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:sensitive:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Sensitive sensitive, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "敏感词"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Sensitive> page = sensitiveService.findPage(new Page<Sensitive>(request, response, -1), sensitive);
    		new ExportExcel("敏感词", Sensitive.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出敏感词记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("cms:sensitive:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Sensitive> list = ei.getDataList(Sensitive.class);
			for (Sensitive sensitive : list){
				try{
					sensitiveService.save(sensitive);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条敏感词记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条敏感词记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入敏感词失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入敏感词数据模板
	 */
	@ResponseBody
	@RequiresPermissions("cms:sensitive:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "敏感词数据导入模板.xlsx";
    		List<Sensitive> list = Lists.newArrayList(); 
    		new ExportExcel("敏感词数据", Sensitive.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}