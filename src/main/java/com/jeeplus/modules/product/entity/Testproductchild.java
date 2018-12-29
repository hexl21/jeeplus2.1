/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.product.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品的副表Entity
 * @author 某人2
 * @version 2018-12-26
 */
public class Testproductchild extends DataEntity<Testproductchild> {
	
	private static final long serialVersionUID = 1L;
	private Testproduct productmain;		// 主表的id 父类
	private String productstatus;		// 状态
	
	public Testproductchild() {
		super();
	}

	public Testproductchild(String id){
		super(id);
	}

	public Testproductchild(Testproduct productmain){
		this.productmain = productmain;
	}

	public Testproduct getProductmain() {
		return productmain;
	}

	public void setProductmain(Testproduct productmain) {
		this.productmain = productmain;
	}
	
	@ExcelField(title="状态", align=2, sort=8)
	public String getProductstatus() {
		return productstatus;
	}

	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}
	
}