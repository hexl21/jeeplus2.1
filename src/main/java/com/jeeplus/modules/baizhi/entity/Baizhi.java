/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.baizhi.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 这是一个测试类Entity
 * @author 某人
 * @version 2018-12-27
 */
public class Baizhi extends DataEntity<Baizhi> {
	
	private static final long serialVersionUID = 1L;
	private String baizhiname;		// 名字
	private Double baizhiprice;		// 价格
	private String baizhisge;		// 年龄
	
	public Baizhi() {
		super();
	}

	public Baizhi(String id){
		super(id);
	}

	@ExcelField(title="名字", align=2, sort=7)
	public String getBaizhiname() {
		return baizhiname;
	}

	public void setBaizhiname(String baizhiname) {
		this.baizhiname = baizhiname;
	}
	
	@ExcelField(title="价格", align=2, sort=8)
	public Double getBaizhiprice() {
		return baizhiprice;
	}

	public void setBaizhiprice(Double baizhiprice) {
		this.baizhiprice = baizhiprice;
	}
	
	@ExcelField(title="年龄", align=2, sort=9)
	public String getBaizhisge() {
		return baizhisge;
	}

	public void setBaizhisge(String baizhisge) {
		this.baizhisge = baizhisge;
	}
	
}