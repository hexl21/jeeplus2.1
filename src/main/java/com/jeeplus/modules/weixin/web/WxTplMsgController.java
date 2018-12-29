/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.weixin.entity.WxAccount;
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
import com.jeeplus.modules.weixin.entity.WxTplMsg;
import com.jeeplus.modules.weixin.service.WxTplMsgService;

/**
 * 模板消息Controller
 * @author toteny
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/wxTplMsg")
public class WxTplMsgController extends BaseController {

	@Autowired
	private WxTplMsgService wxTplMsgService;
	
	@ModelAttribute
	public WxTplMsg get(@RequestParam(required=false) String id) {
		WxTplMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxTplMsgService.get(id);
		}
		if (entity == null){
			entity = new WxTplMsg();
		}
		return entity;
	}
	
	/**
	 * 模板消息列表页面
	 */
	@RequiresPermissions("weixin:wxTplMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list(WxTplMsg wxTplMsg, Model model) {
		model.addAttribute("wxTplMsg", wxTplMsg);
		return "modules/weixin/tplMsg/wxTplMsgList";
	}
	
		/**
	 * 模板消息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxTplMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxTplMsg wxTplMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
		wxTplMsg.setAccount(account.getAccount());
		Page<WxTplMsg> page = wxTplMsgService.findPage(new Page<WxTplMsg>(request, response), wxTplMsg);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑模板消息表单页面
	 */
	@RequiresPermissions(value={"weixin:wxTplMsg:view","weixin:wxTplMsg:add","weixin:wxTplMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxTplMsg wxTplMsg, Model model) {
		model.addAttribute("wxTplMsg", wxTplMsg);
		return "modules/weixin/tplMsg/wxTplMsgForm";
	}

	/**
	 * 保存模板消息
	 */
	@ResponseBody
	@RequiresPermissions(value={"weixin:wxTplMsg:add","weixin:wxTplMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WxTplMsg wxTplMsg, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(wxTplMsg);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		WxAccount account = (WxAccount) CacheUtils.get("WxAccount");
		wxTplMsg.setAccount(account.getAccount());
		//新增或编辑表单保存
		wxTplMsgService.save(wxTplMsg);//保存
		j.setSuccess(true);
		j.setMsg("保存模板消息成功");
		return j;
	}
	
	/**
	 * 删除模板消息
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxTplMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxTplMsg wxTplMsg) {
		AjaxJson j = new AjaxJson();
		wxTplMsgService.delete(wxTplMsg);
		j.setMsg("删除模板消息成功");
		return j;
	}
	
	/**
	 * 批量删除模板消息
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxTplMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxTplMsgService.delete(wxTplMsgService.get(id));
		}
		j.setMsg("删除模板消息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxTplMsg:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(WxTplMsg wxTplMsg, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模板消息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxTplMsg> page = wxTplMsgService.findPage(new Page<WxTplMsg>(request, response, -1), wxTplMsg);
    		new ExportExcel("模板消息", WxTplMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出模板消息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxTplMsg:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxTplMsg> list = ei.getDataList(WxTplMsg.class);
			for (WxTplMsg wxTplMsg : list){
				try{
					wxTplMsgService.save(wxTplMsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模板消息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条模板消息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入模板消息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入模板消息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("weixin:wxTplMsg:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模板消息数据导入模板.xlsx";
    		List<WxTplMsg> list = Lists.newArrayList(); 
    		new ExportExcel("模板消息数据", WxTplMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}