/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

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
import com.jeeplus.modules.weixin.entity.WxMsgBaseSub;
import com.jeeplus.modules.weixin.service.WxMsgBaseSubService;

/**
 * 微信消息Controller
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxMsgBaseSub")
public class WxMsgBaseSubController extends BaseController {

	@Autowired
	private WxMsgBaseSubService wxMsgBaseSubService;
	
	@ModelAttribute
	public WxMsgBaseSub get(@RequestParam(required=false) String id) {
		WxMsgBaseSub entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMsgBaseSubService.get(id);
		}
		if (entity == null){
			entity = new WxMsgBaseSub();
		}
		return entity;
	}
	
	/**
	 * 微信消息列表页面
	 */
	@RequiresPermissions("weixin:wxMsgBaseSub:list")
	@RequestMapping(value = {"list", ""})
	public String list(WxMsgBaseSub wxMsgBaseSub, Model model) {
		model.addAttribute("wxMsgBaseSub", wxMsgBaseSub);
		return "modules/weixin/msg/wxMsgBaseSubList";
	}
	
		/**
	 * 微信消息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMsgBaseSub:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMsgBaseSub wxMsgBaseSub, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMsgBaseSub> page = wxMsgBaseSubService.findPage(new Page<WxMsgBaseSub>(request, response), wxMsgBaseSub); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑微信消息表单页面
	 */
	@RequiresPermissions(value={"weixin:wxMsgBaseSub:view","weixin:wxMsgBaseSub:add","weixin:wxMsgBaseSub:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMsgBaseSub wxMsgBaseSub, Model model) {
		model.addAttribute("wxMsgBaseSub", wxMsgBaseSub);
		return "modules/weixin/msg/wxMsgBaseSubForm";
	}

	/**
	 * 保存微信消息
	 */
	@ResponseBody
	@RequiresPermissions(value={"weixin:wxMsgBaseSub:add","weixin:wxMsgBaseSub:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WxMsgBaseSub wxMsgBaseSub, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(wxMsgBaseSub);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		wxMsgBaseSubService.save(wxMsgBaseSub);//保存
		j.setSuccess(true);
		j.setMsg("保存微信消息成功");
		return j;
	}
	
	/**
	 * 删除微信消息
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMsgBaseSub:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMsgBaseSub wxMsgBaseSub) {
		AjaxJson j = new AjaxJson();
		wxMsgBaseSubService.delete(wxMsgBaseSub);
		j.setMsg("删除微信消息成功");
		return j;
	}
	
	/**
	 * 批量删除微信消息
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMsgBaseSub:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxMsgBaseSubService.delete(wxMsgBaseSubService.get(id));
		}
		j.setMsg("删除微信消息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMsgBaseSub:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(WxMsgBaseSub wxMsgBaseSub, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "微信消息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxMsgBaseSub> page = wxMsgBaseSubService.findPage(new Page<WxMsgBaseSub>(request, response, -1), wxMsgBaseSub);
    		new ExportExcel("微信消息", WxMsgBaseSub.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出微信消息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMsgBaseSub:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMsgBaseSub> list = ei.getDataList(WxMsgBaseSub.class);
			for (WxMsgBaseSub wxMsgBaseSub : list){
				try{
					wxMsgBaseSubService.save(wxMsgBaseSub);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微信消息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条微信消息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入微信消息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入微信消息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxMsgBaseSub:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "微信消息数据导入模板.xlsx";
    		List<WxMsgBaseSub> list = Lists.newArrayList(); 
    		new ExportExcel("微信消息数据", WxMsgBaseSub.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}