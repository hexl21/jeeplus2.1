/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.book_category.entity;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 管理类别Entity
 * @author 高龙
 * @version 2019-01-07
 */
public class Category extends DataEntity<Category> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 类别名
	
	public Category() {
		super();
	}

	public Category(String id){
		super(id);
	}

	@ExcelField(title="类别名", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}