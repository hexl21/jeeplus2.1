/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tree.tree.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 树的测试Entity
 * @author 某人3
 * @version 2018-12-27
 */
public class Treee extends TreeEntity<Treee> {
	
	private static final long serialVersionUID = 1L;
	private String sheng;		// 省
	private String shi;		// 市
	private String xian;		// 县
	private String zhen;		// 镇
	
	
	public Treee() {
		super();
	}

	public Treee(String id){
		super(id);
	}

	public String getSheng() {
		return sheng;
	}

	public void setSheng(String sheng) {
		this.sheng = sheng;
	}
	
	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}
	
	public String getXian() {
		return xian;
	}

	public void setXian(String xian) {
		this.xian = xian;
	}
	
	public String getZhen() {
		return zhen;
	}

	public void setZhen(String zhen) {
		this.zhen = zhen;
	}
	
	public  Treee getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Treee parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}