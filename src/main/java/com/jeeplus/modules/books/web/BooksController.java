/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.books.web;

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
import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.books.service.BooksService;

/**
 * 管理图书Controller
 * @author 高龙
 * @version 2019-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/books/books")
public class BooksController extends BaseController {

	@Autowired
	private BooksService booksService;
	
	@ModelAttribute
	public Books get(@RequestParam(required=false) String id) {
		Books entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = booksService.get(id);
		}
		if (entity == null){
			entity = new Books();
		}
		return entity;
	}
	
	/**
	 * 管理图书列表页面
	 */
	@RequiresPermissions("books:books:list")
	@RequestMapping(value = {"list", ""})
	public String list(Books books, Model model) {
		model.addAttribute("books", books);
		return "modules/books/booksList";
	}
	
		/**
	 * 管理图书列表数据
	 */
	@ResponseBody
	@RequiresPermissions("books:books:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Books books, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Books> page = booksService.findPage(new Page<Books>(request, response), books); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑管理图书表单页面
	 */
	@RequiresPermissions(value={"books:books:view","books:books:add","books:books:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Books books, Model model) {
		model.addAttribute("books", books);
		return "modules/books/booksForm";
	}

	/**
	 * 保存管理图书
	 */
	@ResponseBody
	@RequiresPermissions(value={"books:books:add","books:books:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Books books, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(books);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		booksService.save(books);//保存
		j.setSuccess(true);
		j.setMsg("保存管理图书成功");
		return j;
	}
	
	/**
	 * 删除管理图书
	 */
	@ResponseBody
	@RequiresPermissions("books:books:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Books books) {
		AjaxJson j = new AjaxJson();
		booksService.delete(books);
		j.setMsg("删除管理图书成功");
		return j;
	}
	
	/**
	 * 批量删除管理图书
	 */
	@ResponseBody
	@RequiresPermissions("books:books:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			booksService.delete(booksService.get(id));
		}
		j.setMsg("删除管理图书成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("books:books:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Books books, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "管理图书"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Books> page = booksService.findPage(new Page<Books>(request, response, -1), books);
    		new ExportExcel("管理图书", Books.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出管理图书记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("books:books:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Books> list = ei.getDataList(Books.class);
			for (Books books : list){
				try{
					booksService.save(books);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条管理图书记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条管理图书记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入管理图书失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入管理图书数据模板
	 */
	@ResponseBody
	@RequiresPermissions("books:books:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "管理图书数据导入模板.xlsx";
    		List<Books> list = Lists.newArrayList(); 
    		new ExportExcel("管理图书数据", Books.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}