/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 模板消息Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxTplMsgSub extends DataEntity<WxTplMsgSub> {
	
	private static final long serialVersionUID = 1L;
	private String tplId;		// 模板ID
	private String name;		// name
	private String title;		// 消息标题
	private String sort;		// 排序
	
	public WxTplMsgSub() {
		super();
	}

	public WxTplMsgSub(String id){
		super(id);
	}

	@ExcelField(title="模板ID", align=2, sort=1)
	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}
	
	@ExcelField(title="name", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="消息标题", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="排序", align=2, sort=5)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}