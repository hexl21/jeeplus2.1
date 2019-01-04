/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.students.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.students.entity.Students;
import com.jeeplus.modules.students.service.StudentsService;

/**
 * 管理学生Controller
 * @author 高龙
 * @version 2019-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/students/students")
public class StudentsController extends BaseController {

	@Autowired
	private StudentsService studentsService;
	
	@ModelAttribute
	public Students get(@RequestParam(required=false) String id) {
		Students entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentsService.get(id);
		}
		if (entity == null){
			entity = new Students();
		}
		return entity;
	}
	
	/**
	 * 管理学生列表页面
	 */
	@RequiresPermissions("students:students:list")
	@RequestMapping(value = {"list", ""})
	public String list(Students students, Model model) {
		model.addAttribute("students", students);
		return "modules/students/studentsList";
	}
	
		/**
	 * 管理学生列表数据
	 */
	@ResponseBody
	@RequiresPermissions("students:students:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Students students, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Students> page = studentsService.findPage(new Page<Students>(request, response), students); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑管理学生表单页面
	 */
	@RequiresPermissions(value={"students:students:view","students:students:add","students:students:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Students students, Model model) {
		model.addAttribute("students", students);
		model.addAttribute("mode", mode);
		return "modules/students/studentsForm";
	}

	/**
	 * 保存管理学生
	 */
	@ResponseBody
	@RequiresPermissions(value={"students:students:add","students:students:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Students students, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(students);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		studentsService.save(students);//保存
		j.setSuccess(true);
		j.setMsg("保存管理学生成功");
		return j;
	}
	
	/**
	 * 删除管理学生
	 */
	@ResponseBody
	@RequiresPermissions("students:students:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Students students) {
		AjaxJson j = new AjaxJson();
		studentsService.delete(students);
		j.setMsg("删除管理学生成功");
		return j;
	}
	
	/**
	 * 批量删除管理学生
	 */
	@ResponseBody
	@RequiresPermissions("students:students:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			studentsService.delete(studentsService.get(id));
		}
		j.setMsg("删除管理学生成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("students:students:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Students students, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "管理学生"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Students> page = studentsService.findPage(new Page<Students>(request, response, -1), students);
    		new ExportExcel("管理学生", Students.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出管理学生记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("students:students:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Students> list = ei.getDataList(Students.class);
			for (Students students : list){
				try{
					studentsService.save(students);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条管理学生记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条管理学生记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入管理学生失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入管理学生数据模板
	 */
	@ResponseBody
	@RequiresPermissions("students:students:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "管理学生数据导入模板.xlsx";
    		List<Students> list = Lists.newArrayList(); 
    		new ExportExcel("管理学生数据", Students.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}