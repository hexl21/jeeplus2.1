/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.google.common.collect.Lists;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 微信消息Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxMsgBase extends DataEntity<WxMsgBase> {
	
	private static final long serialVersionUID = 1L;
	private String msgType;		// 消息类型
	private String inputCode;		// 关键字
	private String enable;		// 是否可用
	private String readCount;		// 消息阅读数
	private String favourCount;		// 消息点赞数
	private String foreignId;		// 外键
	private String foreignType;		// 类型：1文本，2图文，3图片，4语音，5视频
	private String rule;		// 匹配规则：1全匹配，2半匹配
	private String foreignTitle;		// 外键
	private String account;        // 微信账号
	private List<WxMsgBaseSub> subList= Lists.newArrayList();

	public WxMsgBase() {
		super();
	}

	public WxMsgBase(String id){
		super(id);
	}

	@ExcelField(title="消息类型", align=2, sort=1)
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@ExcelField(title="关键字", align=2, sort=2)
	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}
	
	@ExcelField(title="是否可用", dictType="yes_no", align=2, sort=3)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	@ExcelField(title="消息阅读数", align=2, sort=4)
	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	
	@ExcelField(title="消息点赞数", align=2, sort=5)
	public String getFavourCount() {
		return favourCount;
	}

	public void setFavourCount(String favourCount) {
		this.favourCount = favourCount;
	}
	
	@ExcelField(title="外键", align=2, sort=6)
	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}
	
	@ExcelField(title="类型：1文本，2图文，3图片，4语音，5视频", align=2, sort=7)
	public String getForeignType() {
		return foreignType;
	}

	public void setForeignType(String foreignType) {
		this.foreignType = foreignType;
	}
	
	@ExcelField(title="匹配规则：1全匹配，2半匹配", align=2, sort=8)
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
	@ExcelField(title="外键", align=2, sort=10)
	public String getForeignTitle() {
		return foreignTitle;
	}

	public void setForeignTitle(String foreignTitle) {
		this.foreignTitle = foreignTitle;
	}

	public List<WxMsgBaseSub> getSubList() {
		return subList;
	}

	public void setSubList(List<WxMsgBaseSub> subList) {
		this.subList = subList;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}