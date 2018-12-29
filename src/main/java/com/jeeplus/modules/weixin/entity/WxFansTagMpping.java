/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 微信用户标签Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxFansTagMpping extends DataEntity<WxFansTagMpping> {
	
	private static final long serialVersionUID = 1L;
	private String tagId;		// 标签ID
	private String openId;		// 粉丝openId
	private WxFans fans;
	private String type;
	private String openIds;
	
	public WxFansTagMpping() {
		super();
	}

	public WxFansTagMpping(String id){
		super(id);
	}

	@ExcelField(title="标签ID", align=2, sort=1)
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	@ExcelField(title="粉丝openId", align=2, sort=2)
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public WxFans getFans() {
		return fans;
	}

	public void setFans(WxFans fans) {
		this.fans = fans;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOpenIds() {
		return openIds;
	}

	public void setOpenIds(String openIds) {
		this.openIds = openIds;
	}
}