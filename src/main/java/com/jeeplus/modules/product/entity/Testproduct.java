/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.product.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品的Entity
 * @author 某人2
 * @version 2018-12-26
 */
public class Testproduct extends DataEntity<Testproduct> {
	
	private static final long serialVersionUID = 1L;
	private String productname;		// 名字
	private String productprice;		// 价格
	private List<Testproductchild> testproductchildList = Lists.newArrayList();		// 子表列表
	
	public Testproduct() {
		super();
	}

	public Testproduct(String id){
		super(id);
	}

	@ExcelField(title="名字", align=2, sort=7)
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	@ExcelField(title="价格", align=2, sort=8)
	public String getProductprice() {
		return productprice;
	}

	public void setProductprice(String productprice) {
		this.productprice = productprice;
	}
	
	public List<Testproductchild> getTestproductchildList() {
		return testproductchildList;
	}

	public void setTestproductchildList(List<Testproductchild> testproductchildList) {
		this.testproductchildList = testproductchildList;
	}
}