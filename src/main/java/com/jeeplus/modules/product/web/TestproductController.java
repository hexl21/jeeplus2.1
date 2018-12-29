/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.product.web;

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
import com.jeeplus.modules.product.entity.Testproduct;
import com.jeeplus.modules.product.service.TestproductService;

/**
 * 商品的Controller
 * @author 某人2
 * @version 2018-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/product/testproduct")
public class TestproductController extends BaseController {

	@Autowired
	private TestproductService testproductService;
	
	@ModelAttribute
	public Testproduct get(@RequestParam(required=false) String id) {
		Testproduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testproductService.get(id);
		}
		if (entity == null){
			entity = new Testproduct();
		}
		return entity;
	}
	
	/**
	 * 商品的主表列表页面
	 */
	@RequiresPermissions("product:testproduct:list")
	@RequestMapping(value = {"list", ""})
	public String list(Testproduct testproduct, Model model) {
		model.addAttribute("testproduct", testproduct);
		return "modules/product/testproductList";
	}
	
		/**
	 * 商品的主表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("product:testproduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Testproduct testproduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Testproduct> page = testproductService.findPage(new Page<Testproduct>(request, response), testproduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品的主表表单页面
	 */
	@RequiresPermissions(value={"product:testproduct:view","product:testproduct:add","product:testproduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Testproduct testproduct, Model model) {
		model.addAttribute("testproduct", testproduct);
		return "modules/product/testproductForm";
	}

	/**
	 * 保存商品的主表
	 */
	@ResponseBody
	@RequiresPermissions(value={"product:testproduct:add","product:testproduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Testproduct testproduct, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testproduct);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testproductService.save(testproduct);//保存
		j.setSuccess(true);
		j.setMsg("保存商品的主表成功");
		return j;
	}
	
	/**
	 * 删除商品的主表
	 */
	@ResponseBody
	@RequiresPermissions("product:testproduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Testproduct testproduct) {
		AjaxJson j = new AjaxJson();
		testproductService.delete(testproduct);
		j.setMsg("删除商品的主表成功");
		return j;
	}
	
	/**
	 * 批量删除商品的主表
	 */
	@ResponseBody
	@RequiresPermissions("product:testproduct:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testproductService.delete(testproductService.get(id));
		}
		j.setMsg("删除商品的主表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("product:testproduct:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Testproduct testproduct, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品的主表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Testproduct> page = testproductService.findPage(new Page<Testproduct>(request, response, -1), testproduct);
    		new ExportExcel("商品的主表", Testproduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品的主表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public Testproduct detail(String id) {
		return testproductService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("product:testproduct:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Testproduct> list = ei.getDataList(Testproduct.class);
			for (Testproduct testproduct : list){
				try{
					testproductService.save(testproduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品的主表记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条商品的主表记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入商品的主表失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入商品的主表数据模板
	 */
	@ResponseBody
	@RequiresPermissions("product:testproduct:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品的主表数据导入模板.xlsx";
    		List<Testproduct> list = Lists.newArrayList(); 
    		new ExportExcel("商品的主表数据", Testproduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}