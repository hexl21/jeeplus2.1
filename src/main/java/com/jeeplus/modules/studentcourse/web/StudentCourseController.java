/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.studentcourse.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.studentcourse.entity.StudentCourse;
import com.jeeplus.modules.studentcourse.service.StudentCourseService;

/**
 * 学生的记录表Controller
 * @author 某人10
 * @version 2018-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/studentcourse/studentCourse")
public class StudentCourseController extends BaseController {

	@Autowired
	private StudentCourseService studentCourseService;
	
	@ModelAttribute
	public StudentCourse get(@RequestParam(required=false) String id) {
		StudentCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentCourseService.get(id);
		}
		if (entity == null){
			entity = new StudentCourse();
		}
		return entity;
	}
	
	/**
	 * 课程记录列表页面
	 */
	@RequiresPermissions("studentcourse:studentCourse:list")
	@RequestMapping(value = {"list", ""})
	public String list(StudentCourse studentCourse, Model model) {
		model.addAttribute("studentCourse", studentCourse);
		return "modules/studentcourse/studentCourseList";
	}
	
		/**
	 * 课程记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("studentcourse:studentCourse:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StudentCourse studentCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentCourse> page = studentCourseService.findPage(new Page<StudentCourse>(request, response), studentCourse); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑课程记录表单页面
	 */
	@RequiresPermissions(value={"studentcourse:studentCourse:view","studentcourse:studentCourse:add","studentcourse:studentCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StudentCourse studentCourse, Model model) {
		model.addAttribute("studentCourse", studentCourse);
		return "modules/studentcourse/studentCourseForm";
	}

	/**
	 * 保存课程记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"studentcourse:studentCourse:add","studentcourse:studentCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StudentCourse studentCourse, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(studentCourse);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		studentCourseService.save(studentCourse);//保存
		j.setSuccess(true);
		j.setMsg("保存课程记录成功");
		return j;
	}
	
	/**
	 * 删除课程记录
	 */
	@ResponseBody
	@RequiresPermissions("studentcourse:studentCourse:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StudentCourse studentCourse) {
		AjaxJson j = new AjaxJson();
		studentCourseService.delete(studentCourse);
		j.setMsg("删除课程记录成功");
		return j;
	}
	
	/**
	 * 批量删除课程记录
	 */
	@ResponseBody
	@RequiresPermissions("studentcourse:studentCourse:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			studentCourseService.delete(studentCourseService.get(id));
		}
		j.setMsg("删除课程记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("studentcourse:studentCourse:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(StudentCourse studentCourse, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "课程记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StudentCourse> page = studentCourseService.findPage(new Page<StudentCourse>(request, response, -1), studentCourse);
    		new ExportExcel("课程记录", StudentCourse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出课程记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("studentcourse:studentCourse:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StudentCourse> list = ei.getDataList(StudentCourse.class);
			for (StudentCourse studentCourse : list){
				try{
					studentCourseService.save(studentCourse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条课程记录记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条课程记录记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入课程记录失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入课程记录数据模板
	 */
	@ResponseBody
	@RequiresPermissions("studentcourse:studentCourse:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "课程记录数据导入模板.xlsx";
    		List<StudentCourse> list = Lists.newArrayList(); 
    		new ExportExcel("课程记录数据", StudentCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}