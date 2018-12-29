/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 微信消息Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxMsgBaseSub extends DataEntity<WxMsgBaseSub> {
	
	private static final long serialVersionUID = 1L;
	private String baseId;		// base_id
	private String foreignId;		// 外键
	private String foreignType;		// 类型：1文本，2图文，3图片，4语音，5视频
	private String foreignTitle;		// 外键
	private String rule;		// 匹配规则：1全匹配，2半匹配
	
	public WxMsgBaseSub() {
		super();
	}

	public WxMsgBaseSub(String id){
		super(id);
	}

	@ExcelField(title="base_id", align=2, sort=1)
	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	
	@ExcelField(title="外键", align=2, sort=3)
	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}
	
	@ExcelField(title="类型：1文本，2图文，3图片，4语音，5视频", align=2, sort=4)
	public String getForeignType() {
		return foreignType;
	}

	public void setForeignType(String foreignType) {
		this.foreignType = foreignType;
	}
	
	@ExcelField(title="外键", align=2, sort=6)
	public String getForeignTitle() {
		return foreignTitle;
	}

	public void setForeignTitle(String foreignTitle) {
		this.foreignTitle = foreignTitle;
	}
	
	@ExcelField(title="匹配规则：1全匹配，2半匹配", dictType="yes_no", align=2, sort=7)
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
}