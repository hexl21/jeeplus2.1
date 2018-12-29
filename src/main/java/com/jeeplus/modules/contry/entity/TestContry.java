/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contry.entity;

import com.jeeplus.modules.continent.entity.TestContinent;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 国家Entity
 * @author 某人7
 * @version 2018-12-27
 */
public class TestContry extends DataEntity<TestContry> {
	
	private static final long serialVersionUID = 1L;
	private TestContinent continent;		// 所属洲
	private String continentpeople;		// 人
	
	public TestContry() {
		super();
	}

	public TestContry(String id){
		super(id);
	}

	@ExcelField(title="所属洲", fieldType=TestContinent.class, value="", align=2, sort=7)
	public TestContinent getContinent() {
		return continent;
	}

	public void setContinent(TestContinent continent) {
		this.continent = continent;
	}
	
	@ExcelField(title="人", align=2, sort=8)
	public String getContinentpeople() {
		return continentpeople;
	}

	public void setContinentpeople(String continentpeople) {
		this.continentpeople = continentpeople;
	}
	
}