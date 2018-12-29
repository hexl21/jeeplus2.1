/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tree.tree.web;

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
import com.jeeplus.modules.tree.tree.entity.Treee;
import com.jeeplus.modules.tree.tree.service.TreeeService;

/**
 * 树的测试Controller
 * @author 某人3
 * @version 2018-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/tree/tree/treee")
public class TreeeController extends BaseController {

	@Autowired
	private TreeeService treeeService;
	
	@ModelAttribute
	public Treee get(@RequestParam(required=false) String id) {
		Treee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = treeeService.get(id);
		}
		if (entity == null){
			entity = new Treee();
		}
		return entity;
	}
	
	/**
	 * 树的测试列表页面
	 */
	@RequiresPermissions("tree:tree:treee:list")
	@RequestMapping(value = {"list", ""})
	public String list(Treee treee,  HttpServletRequest request, HttpServletResponse response, Model model) {
	
		model.addAttribute("treee", treee);
		return "modules/tree/tree/treeeList";
	}

	/**
	 * 查看，增加，编辑树的测试表单页面
	 */
	@RequiresPermissions(value={"tree:tree:treee:view","tree:tree:treee:add","tree:tree:treee:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Treee treee, Model model) {
		if (treee.getParent()!=null && StringUtils.isNotBlank(treee.getParent().getId())){
			treee.setParent(treeeService.get(treee.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(treee.getId())){
				Treee treeeChild = new Treee();
				treeeChild.setParent(new Treee(treee.getParent().getId()));
				List<Treee> list = treeeService.findList(treee); 
				if (list.size() > 0){
					treee.setSort(list.get(list.size()-1).getSort());
					if (treee.getSort() != null){
						treee.setSort(treee.getSort() + 30);
					}
				}
			}
		}
		if (treee.getSort() == null){
			treee.setSort(30);
		}
		model.addAttribute("treee", treee);
		return "modules/tree/tree/treeeForm";
	}

	/**
	 * 保存树的测试
	 */
	@ResponseBody
	@RequiresPermissions(value={"tree:tree:treee:add","tree:tree:treee:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Treee treee, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(treee);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		treeeService.save(treee);//保存
		j.setSuccess(true);
		j.put("treee", treee);
		j.setMsg("保存树的测试成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Treee> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return treeeService.getChildren(parentId);
	}
	
	/**
	 * 删除树的测试
	 */
	@ResponseBody
	@RequiresPermissions("tree:tree:treee:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Treee treee) {
		AjaxJson j = new AjaxJson();
		treeeService.delete(treee);
		j.setSuccess(true);
		j.setMsg("删除树的测试成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Treee> list = treeeService.findList(new Treee());
		for (int i=0; i<list.size(); i++){
			Treee e = list.get(i);
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