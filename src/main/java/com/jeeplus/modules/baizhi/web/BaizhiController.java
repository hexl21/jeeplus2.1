/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.baizhi.web;

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
import com.jeeplus.modules.baizhi.entity.Baizhi;
import com.jeeplus.modules.baizhi.service.BaizhiService;

/**
 * 这是一个测试类Controller
 * @author 某人
 * @version 2018-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/baizhi/baizhi")
public class BaizhiController extends BaseController {

	@Autowired
	private BaizhiService baizhiService;
	
	@ModelAttribute
	public Baizhi get(@RequestParam(required=false) String id) {
		Baizhi entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baizhiService.get(id);
		}
		if (entity == null){
			entity = new Baizhi();
		}
		return entity;
	}
	
	/**
	 * 百知列表页面
	 */
	@RequiresPermissions("baizhi:baizhi:list")
	@RequestMapping(value = {"list", ""})
	public String list(Baizhi baizhi, Model model) {
		model.addAttribute("baizhi", baizhi);
		return "modules/baizhi/baizhiList";
	}
	
		/**
	 * 百知列表数据
	 */
	@ResponseBody
	@RequiresPermissions("baizhi:baizhi:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Baizhi baizhi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Baizhi> page = baizhiService.findPage(new Page<Baizhi>(request, response), baizhi); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑百知表单页面
	 */
	@RequiresPermissions(value={"baizhi:baizhi:view","baizhi:baizhi:add","baizhi:baizhi:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Baizhi baizhi, Model model) {
		model.addAttribute("baizhi", baizhi);
		return "modules/baizhi/baizhiForm";
	}

	/**
	 * 保存百知
	 */
	@ResponseBody
	@RequiresPermissions(value={"baizhi:baizhi:add","baizhi:baizhi:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Baizhi baizhi, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baizhi);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baizhiService.save(baizhi);//保存
		j.setSuccess(true);
		j.setMsg("保存百知成功");
		return j;
	}
	
	/**
	 * 删除百知
	 */
	@ResponseBody
	@RequiresPermissions("baizhi:baizhi:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Baizhi baizhi) {
		AjaxJson j = new AjaxJson();
		baizhiService.delete(baizhi);
		j.setMsg("删除百知成功");
		return j;
	}
	
	/**
	 * 批量删除百知
	 */
	@ResponseBody
	@RequiresPermissions("baizhi:baizhi:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baizhiService.delete(baizhiService.get(id));
		}
		j.setMsg("删除百知成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("baizhi:baizhi:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Baizhi baizhi, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "百知"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Baizhi> page = baizhiService.findPage(new Page<Baizhi>(request, response, -1), baizhi);
    		new ExportExcel("百知", Baizhi.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出百知记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("baizhi:baizhi:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Baizhi> list = ei.getDataList(Baizhi.class);
			for (Baizhi baizhi : list){
				try{
					baizhiService.save(baizhi);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条百知记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条百知记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入百知失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入百知数据模板
	 */
	@ResponseBody
	@RequiresPermissions("baizhi:baizhi:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "百知数据导入模板.xlsx";
    		List<Baizhi> list = Lists.newArrayList(); 
    		new ExportExcel("百知数据", Baizhi.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}