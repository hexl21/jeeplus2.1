/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fuwenben.web;

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
import com.jeeplus.modules.fuwenben.entity.Note;
import com.jeeplus.modules.fuwenben.service.NoteService;

/**
 * 富文本Controller
 * @author 某人4
 * @version 2018-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/fuwenben/note")
public class NoteController extends BaseController {

	@Autowired
	private NoteService noteService;
	
	@ModelAttribute
	public Note get(@RequestParam(required=false) String id) {
		Note entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = noteService.get(id);
		}
		if (entity == null){
			entity = new Note();
		}
		return entity;
	}
	
	/**
	 * 富文本列表页面
	 */
	@RequiresPermissions("fuwenben:note:list")
	@RequestMapping(value = {"list", ""})
	public String list(Note note, Model model) {
		model.addAttribute("note", note);
		return "modules/fuwenben/noteList";
	}
	
		/**
	 * 富文本列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fuwenben:note:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Note note, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Note> page = noteService.findPage(new Page<Note>(request, response), note); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑富文本表单页面
	 */
	@RequiresPermissions(value={"fuwenben:note:view","fuwenben:note:add","fuwenben:note:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Note note, Model model) {
		model.addAttribute("note", note);
		return "modules/fuwenben/noteForm";
	}

	/**
	 * 保存富文本
	 */
	@ResponseBody
	@RequiresPermissions(value={"fuwenben:note:add","fuwenben:note:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Note note, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(note);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		noteService.save(note);//保存
		j.setSuccess(true);
		j.setMsg("保存富文本成功");
		return j;
	}
	
	/**
	 * 删除富文本
	 */
	@ResponseBody
	@RequiresPermissions("fuwenben:note:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Note note) {
		AjaxJson j = new AjaxJson();
		noteService.delete(note);
		j.setMsg("删除富文本成功");
		return j;
	}
	
	/**
	 * 批量删除富文本
	 */
	@ResponseBody
	@RequiresPermissions("fuwenben:note:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			noteService.delete(noteService.get(id));
		}
		j.setMsg("删除富文本成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fuwenben:note:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Note note, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "富文本"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Note> page = noteService.findPage(new Page<Note>(request, response, -1), note);
    		new ExportExcel("富文本", Note.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出富文本记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("fuwenben:note:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Note> list = ei.getDataList(Note.class);
			for (Note note : list){
				try{
					noteService.save(note);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条富文本记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条富文本记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入富文本失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入富文本数据模板
	 */
	@ResponseBody
	@RequiresPermissions("fuwenben:note:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "富文本数据导入模板.xlsx";
    		List<Note> list = Lists.newArrayList(); 
    		new ExportExcel("富文本数据", Note.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}