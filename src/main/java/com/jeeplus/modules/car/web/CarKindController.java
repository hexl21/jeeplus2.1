/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.car.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.config.Global;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.car.entity.CarKind;
import com.jeeplus.modules.car.service.CarKindService;

/**
 * 车系Controller
 * @author 某人11
 * @version 2018-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carKind")
public class CarKindController extends BaseController {

	@Autowired
	private CarKindService carKindService;
	
	@ModelAttribute
	public CarKind get(@RequestParam(required=false) String id) {
		CarKind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carKindService.get(id);
		}
		if (entity == null){
			entity = new CarKind();
		}
		return entity;
	}
	
	/**
	 * 车列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(CarKind carKind,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/car/carKindList";
	}

	/**
	 * 查看，增加，编辑车表单页面
	 */
	@RequestMapping(value = "form")
	public String form(CarKind carKind, Model model) {
		if (carKind.getParent()!=null && StringUtils.isNotBlank(carKind.getParent().getId())){
			carKind.setParent(carKindService.get(carKind.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(carKind.getId())){
				CarKind carKindChild = new CarKind();
				carKindChild.setParent(new CarKind(carKind.getParent().getId()));
				List<CarKind> list = carKindService.findList(carKind); 
				if (list.size() > 0){
					carKind.setSort(list.get(list.size()-1).getSort());
					if (carKind.getSort() != null){
						carKind.setSort(carKind.getSort() + 30);
					}
				}
			}
		}
		if (carKind.getSort() == null){
			carKind.setSort(30);
		}
		model.addAttribute("carKind", carKind);
		return "modules/car/carKindForm";
	}

	/**
	 * 保存车
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(CarKind carKind, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(carKind);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		carKindService.save(carKind);//保存
		j.setSuccess(true);
		j.put("carKind", carKind);
		j.setMsg("保存车成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<CarKind> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return carKindService.getChildren(parentId);
	}
	
	/**
	 * 删除车
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(CarKind carKind) {
		AjaxJson j = new AjaxJson();
		carKindService.delete(carKind);
		j.setSuccess(true);
		j.setMsg("删除车成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CarKind> list = carKindService.findList(new CarKind());
		for (int i=0; i<list.size(); i++){
			CarKind e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}