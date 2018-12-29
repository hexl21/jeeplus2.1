/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contry.web;

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
import com.jeeplus.modules.contry.entity.TestContry;
import com.jeeplus.modules.contry.service.TestContryService;

/**
 * 国家Controller
 * @author 某人7
 * @version 2018-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/contry/testContry")
public class TestContryController extends BaseController {

	@Autowired
	private TestContryService testContryService;
	
	@ModelAttribute
	public TestContry get(@RequestParam(required=false) String id) {
		TestContry entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testContryService.get(id);
		}
		if (entity == null){
			entity = new TestContry();
		}
		return entity;
	}
	
	/**
	 * 国家列表页面
	 */
	@RequiresPermissions("contry:testContry:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestContry testContry, Model model) {
		model.addAttribute("testContry", testContry);
		return "modules/contry/testContryList";
	}
	
		/**
	 * 国家列表数据
	 */
	@ResponseBody
	@RequiresPermissions("contry:testContry:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestContry testContry, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestContry> page = testContryService.findPage(new Page<TestContry>(request, response), testContry); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑国家表单页面
	 */
	@RequiresPermissions(value={"contry:testContry:view","contry:testContry:add","contry:testContry:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TestContry testContry, Model model) {
		model.addAttribute("testContry", testContry);
		return "modules/contry/testContryForm";
	}

	/**
	 * 保存国家
	 */
	@ResponseBody
	@RequiresPermissions(value={"contry:testContry:add","contry:testContry:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestContry testContry, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testContry);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testContryService.save(testContry);//保存
		j.setSuccess(true);
		j.setMsg("保存国家成功");
		return j;
	}
	
	/**
	 * 删除国家
	 */
	@ResponseBody
	@RequiresPermissions("contry:testContry:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestContry testContry) {
		AjaxJson j = new AjaxJson();
		testContryService.delete(testContry);
		j.setMsg("删除国家成功");
		return j;
	}
	
	/**
	 * 批量删除国家
	 */
	@ResponseBody
	@RequiresPermissions("contry:testContry:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testContryService.delete(testContryService.get(id));
		}
		j.setMsg("删除国家成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("contry:testContry:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestContry testContry, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "国家"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestContry> page = testContryService.findPage(new Page<TestContry>(request, response, -1), testContry);
    		new ExportExcel("国家", TestContry.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出国家记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("contry:testContry:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestContry> list = ei.getDataList(TestContry.class);
			for (TestContry testContry : list){
				try{
					testContryService.save(testContry);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条国家记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条国家记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入国家失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入国家数据模板
	 */
	@ResponseBody
	@RequiresPermissions("contry:testContry:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "国家数据导入模板.xlsx";
    		List<TestContry> list = Lists.newArrayList(); 
    		new ExportExcel("国家数据", TestContry.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}