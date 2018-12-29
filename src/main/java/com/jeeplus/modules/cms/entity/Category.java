/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.core.persistence.TreeEntity;
import com.jeeplus.modules.cms.utils.CmsUtils;
import com.jeeplus.modules.sys.entity.Office;

import java.util.List;

/**
 * 栏目信息Entity
 * @author toteny
 * @version 2018-07-20
 */
public class Category extends TreeEntity<Category> {
	
	private static final long serialVersionUID = 1L;
	private Site site;        // 归属站点
	private Office office;		// 归属机构
	private String module;		// 栏目模块
	private String image;		// 栏目图片
	private String href;		// 链接
	private String target;		// 目标
	private String description;		// 描述
	private String keywords;		// 关键字
	private String inMenu;		// 是否在导航中显示
	private String inList;		// 是否在分类页中显示列表
	private List<Category> childList = Lists.newArrayList();    // 拥有子分类列信息
	private List<Article> articleList = Lists.newArrayList();


	public Category() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}

	public Category(String id) {
		this();
		this.id = id;
	}

	public Category(String id, Site site) {
		this();
		this.id = id;
		this.setSite(site);
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getInMenu() {
		return inMenu;
	}

	public void setInMenu(String inMenu) {
		this.inMenu = inMenu;
	}
	
	public String getInList() {
		return inList;
	}

	public void setInList(String inList) {
		this.inList = inList;
	}
	
	public  Category getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Category parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public static void sortList(List<Category> list, List<Category> sourcelist, String parentId, boolean cascade) {
		for (int i = 0; i < sourcelist.size(); i++) {
			Category e = sourcelist.get(i);
			if (e.getParent() != null && e.getParent().getId() != null && e.getParent().getId().equals(parentId)) {
				list.add(e);
				if (cascade) {   // 判断是否还有子节点, 有则继续获取子节点
					for (int j = 0; j < sourcelist.size(); j++) {
						Category child = sourcelist.get(j);
						if (child.getParent() != null && child.getParent().getId() != null
								&& child.getParent().getId().equals(e.getId())) {
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	public String getIds() {
		return (this.getParentIds() != null ? this.getParentIds().replaceAll(",", " ") : "")
				+ (this.getId() != null ? this.getId() : "");
	}

	@JsonIgnore
	public static String getRootId() {
		return "1";
	}


	public boolean isRoot() {
		return isRoot(this.id);
	}

	public static boolean isRoot(String id) {
		return id != null && id.equals("1");
	}

	public String getUrl() {
		return CmsUtils.getUrlDynamic(this);
	}

}