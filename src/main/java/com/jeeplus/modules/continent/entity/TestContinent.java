/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.continent.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 五大洲Entity
 * @author 某人7
 * @version 2018-12-27
 */
public class TestContinent extends DataEntity<TestContinent> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 洲名
	
	public TestContinent() {
		super();
	}

	public TestContinent(String id){
		super(id);
	}

	@ExcelField(title="洲名", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}