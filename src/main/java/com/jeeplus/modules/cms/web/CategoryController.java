/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cms.web;

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
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.cms.entity.Category;
import com.jeeplus.modules.cms.service.CategoryService;

/**
 * 栏目信息Controller
 *
 * @author toteny
 * @version 2018-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public Category get(@RequestParam(required = false) String id) {
        Category entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = categoryService.get(id);
        }
        if (entity == null) {
            entity = new Category();
        }
        return entity;
    }

    /**
     * 栏目信息列表页面
     */
    @RequiresPermissions("cms:category:list")
    @RequestMapping(value = {"list", ""})
    public String list(Category category, HttpServletRequest request, HttpServletResponse response, Model model) {

        model.addAttribute("category", category);
        return "modules/cms/category/categoryList";
    }

    /**
     * 查看，增加，编辑栏目信息表单页面
     */
    @RequiresPermissions(value = {"cms:category:view", "cms:category:add", "cms:category:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Category category, Model model) {
        if (category.getParent() != null && StringUtils.isNotBlank(category.getParent().getId())) {
            category.setParent(categoryService.get(category.getParent().getId()));
            // 获取排序号，最末节点排序号+30
            if (StringUtils.isBlank(category.getId())) {
                Category categoryChild = new Category();
                categoryChild.setParent(new Category(category.getParent().getId()));
                List<Category> list = categoryService.findList(category);
                if (list.size() > 0) {
                    category.setSort(list.get(list.size() - 1).getSort());
                    if (category.getSort() != null) {
                        category.setSort(category.getSort() + 30);
                    }
                }
            }
        }
        if (category.getSort() == null) {
            category.setSort(30);
        }
        model.addAttribute("category", category);
        return "modules/cms/category/categoryForm";
    }

    /**
     * 保存栏目信息
     */
    @ResponseBody
    @RequiresPermissions(value = {"cms:category:add", "cms:category:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public AjaxJson save(Category category, Model model) throws Exception {
        AjaxJson j = new AjaxJson();
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = beanValidator(category);
        if (StringUtils.isNotBlank(errMsg)) {
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }

        //新增或编辑表单保存
        categoryService.save(category);//保存
        j.setSuccess(true);
        j.put("category", category);
        j.setMsg("保存栏目信息成功");
        return j;
    }

    @ResponseBody
    @RequestMapping(value = "getChildren")
    public List<Category> getChildren(String parentId) {
        if ("-1".equals(parentId)) {//如果是-1，没指定任何父节点，就从根节点开始查找
            parentId = "1";
        }
        if (StringUtils.isEmpty(parentId)) {
            parentId = "1";
        }
        return categoryService.getChildren(parentId);
    }

    /**
     * 删除栏目信息
     */
    @ResponseBody
    @RequiresPermissions("cms:category:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(Category category) {
        AjaxJson j = new AjaxJson();
        categoryService.delete(category);
        j.setSuccess(true);
        j.setMsg("删除栏目信息成功");
        return j;
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Category> list = categoryService.findList(new Category());
        for (int i = 0; i < list.size(); i++) {
            Category e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("text", e.getName());
                if (StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())) {
                    map.put("parent", "#");
                    Map<String, Object> state = Maps.newHashMap();
                    state.put("opened", true);
                    map.put("state", state);
                } else {
                    map.put("parent", e.getParentId());
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

}