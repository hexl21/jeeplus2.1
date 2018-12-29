/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 公众号Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxAccount extends DataEntity<WxAccount> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 公众号名称
	private String account;		// 公众号账号
	private String appid;		// appid
	private String appsecret;		// appsecret
	private String url;		// 验证时用的url
	private String token;		// token
	private String msgCount;		// 自动回复消息条数;默认是5条
	private String mchId;//商户号
	private String mchName;//商户名称
	private String mchKey;//商户密钥
	private String isDefault;		// 是否默认
	
	public WxAccount() {
		super();
	}

	public WxAccount(String id){
		super(id);
	}

	@ExcelField(title="公众号名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="公众号账号", align=2, sort=2)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@ExcelField(title="appid", align=2, sort=3)
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@ExcelField(title="appsecret", align=2, sort=4)
	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	
	@ExcelField(title="验证时用的url", align=2, sort=5)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="token", align=2, sort=6)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@ExcelField(title="自动回复消息条数;默认是5条", align=2, sort=7)
	public String getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(String msgCount) {
		this.msgCount = msgCount;
	}
	
	@ExcelField(title="是否默认", align=2, sort=14)
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getMchName() {
		return mchName;
	}

	public void setMchName(String mchName) {
		this.mchName = mchName;
	}

	public String getMchKey() {
		return mchKey;
	}

	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}
}