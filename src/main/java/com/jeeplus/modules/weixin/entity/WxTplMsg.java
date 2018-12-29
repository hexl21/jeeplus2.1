/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.google.common.collect.Lists;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 模板消息Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxTplMsg extends DataEntity<WxTplMsg> {
	
	private static final long serialVersionUID = 1L;
	private String tplId;		// 模板ID
	private String title;		// 消息标题
	private String content;		// 消息内容
	private String wxTpl;		// 微信模板
	private String account;		// account
	private String url;		// 访问地址
	private List<WxTplMsgSub> subList= Lists.newArrayList();
	
	public WxTplMsg() {
		super();
	}

	public WxTplMsg(String id){
		super(id);
	}

	@ExcelField(title="模板ID", align=2, sort=1)
	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}
	
	@ExcelField(title="消息标题", align=2, sort=2)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="消息内容", align=2, sort=3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="微信模板", align=2, sort=4)
	public String getWxTpl() {
		return wxTpl;
	}

	public void setWxTpl(String wxTpl) {
		this.wxTpl = wxTpl;
	}
	
	@ExcelField(title="account", align=2, sort=5)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@ExcelField(title="访问地址", align=2, sort=6)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<WxTplMsgSub> getSubList() {
		return subList;
	}

	public void setSubList(List<WxTplMsgSub> subList) {
		this.subList = subList;
	}
}