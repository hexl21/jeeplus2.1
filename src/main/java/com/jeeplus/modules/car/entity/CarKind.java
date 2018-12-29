/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.car.entity;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 车系Entity
 * @author 某人11
 * @version 2018-12-27
 */
public class CarKind extends TreeEntity<CarKind> {
	
	private static final long serialVersionUID = 1L;
	
	
	public CarKind() {
		super();
	}

	public CarKind(String id){
		super(id);
	}

	public  CarKind getParent() {
			return parent;
	}
	
	@Override
	public void setParent(CarKind parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}