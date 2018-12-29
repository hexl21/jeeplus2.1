/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户标签Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxFansTag extends DataEntity<WxFansTag> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 标签名称
	private Integer count;		// 粉丝数量
	private String account;		// 微信号
	
	public WxFansTag() {
		super();
	}

	public WxFansTag(String id){
		super(id);
	}

	@ExcelField(title="标签名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="粉丝数量", align=2, sort=2)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	@ExcelField(title="微信号", align=2, sort=9)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}