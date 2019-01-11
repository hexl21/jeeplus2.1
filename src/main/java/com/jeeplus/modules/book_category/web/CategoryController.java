/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.book_category.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.book_category.entity.Category;
import com.jeeplus.modules.book_category.service.CategoryService;
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
 * 管理类别Controller
 * @author 高龙
 * @version 2019-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/book_category/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public Category get(@RequestParam(required=false) String id) {
		Category entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = categoryService.get(id);
		}
		if (entity == null){
			entity = new Category();
		}
		return entity;
	}
	
	/**
	 * 管理类别列表页面
	 */
	@RequiresPermissions("book_category:category:list")
	@RequestMapping(value = {"list", ""})
	public String list(Category category, Model model) {
		model.addAttribute("category", category);
		return "modules/book_category/categoryList";
	}
	
		/**
	 * 管理类别列表数据
	 */
	@ResponseBody
	@RequiresPermissions("book_category:category:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Category category, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Category> page = categoryService.findPage(new Page<Category>(request, response), category); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑管理类别表单页面
	 */
	@RequiresPermissions(value={"book_category:category:view","book_category:category:add","book_category:category:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		model.addAttribute("category", category);
		return "modules/book_category/categoryForm";
	}

	/**
	 * 保存管理类别
	 */
	@ResponseBody
	@RequiresPermissions(value={"book_category:category:add","book_category:category:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Category category, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(category);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		categoryService.save(category);//保存
		j.setSuccess(true);
		j.setMsg("保存管理类别成功");
		return j;
	}
	
	/**
	 * 删除管理类别
	 */
	@ResponseBody
	@RequiresPermissions("book_category:category:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Category category) {
		AjaxJson j = new AjaxJson();
		categoryService.delete(category);
		j.setMsg("删除管理类别成功");
		return j;
	}
	
	/**
	 * 批量删除管理类别
	 */
	@ResponseBody
	@RequiresPermissions("book_category:category:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			categoryService.delete(categoryService.get(id));
		}
		j.setMsg("删除管理类别成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("book_category:category:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Category category, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "管理类别"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Category> page = categoryService.findPage(new Page<Category>(request, response, -1), category);
    		new ExportExcel("管理类别", Category.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出管理类别记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("book_category:category:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Category> list = ei.getDataList(Category.class);
			for (Category category : list){
				try{
					categoryService.save(category);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条管理类别记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条管理类别记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入管理类别失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入管理类别数据模板
	 */
	@ResponseBody
	@RequiresPermissions("book_category:category:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "管理类别数据导入模板.xlsx";
    		List<Category> list = Lists.newArrayList(); 
    		new ExportExcel("管理类别数据", Category.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}