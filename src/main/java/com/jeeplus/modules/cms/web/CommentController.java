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
import com.jeeplus.modules.cms.entity.Comment;
import com.jeeplus.modules.cms.service.CommentService;

/**
 * 评论信息Controller
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/comment")
public class CommentController extends BaseController {

	@Autowired
	private CommentService commentService;
	
	@ModelAttribute
	public Comment get(@RequestParam(required=false) String id) {
		Comment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commentService.get(id);
		}
		if (entity == null){
			entity = new Comment();
		}
		return entity;
	}
	
	/**
	 * 评论信息列表页面
	 */
	@RequiresPermissions("cms:comment:list")
	@RequestMapping(value = {"list", ""})
	public String list(Comment comment, Model model) {
		model.addAttribute("comment", comment);
		return "modules/cms/comment/commentList";
	}
	
		/**
	 * 评论信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Comment> page = commentService.findPage(new Page<Comment>(request, response), comment); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑评论信息表单页面
	 */
	@RequiresPermissions(value={"cms:comment:view","cms:comment:add","cms:comment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Comment comment, Model model) {
		model.addAttribute("comment", comment);
		return "modules/cms/comment/commentForm";
	}

	/**
	 * 保存评论信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"cms:comment:add","cms:comment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Comment comment, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(comment);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		commentService.save(comment);//保存
		j.setSuccess(true);
		j.setMsg("保存评论信息成功");
		return j;
	}
	
	/**
	 * 删除评论信息
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Comment comment) {
		AjaxJson j = new AjaxJson();
		commentService.delete(comment);
		j.setMsg("删除评论信息成功");
		return j;
	}
	
	/**
	 * 批量删除评论信息
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			commentService.delete(commentService.get(id));
		}
		j.setMsg("删除评论信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Comment comment, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "评论信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Comment> page = commentService.findPage(new Page<Comment>(request, response, -1), comment);
    		new ExportExcel("评论信息", Comment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出评论信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Comment> list = ei.getDataList(Comment.class);
			for (Comment comment : list){
				try{
					commentService.save(comment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条评论信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条评论信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入评论信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入评论信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "评论信息数据导入模板.xlsx";
    		List<Comment> list = Lists.newArrayList(); 
    		new ExportExcel("评论信息数据", Comment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}