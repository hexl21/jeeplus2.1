/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.persistence.DataEntity;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 微信粉丝Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxFans extends DataEntity<WxFans> {

	private static final long serialVersionUID = 1L;
	private String openId;        // openId
	private String subscribeStatus;        // 订阅状态
	private Date subscribeTime;        // 订阅时间
	private byte[] nickName;        // 昵称
	private String sex;        // 性别 0-女；1-男；2-未知
	private String language;        // 语言
	private String country;        // 国家
	private String province;        // 省
	private String city;        // 城市
	private String headImgUrl;        // 头像
	private String status;        // 用户状态 1-可用；0-不可用
	private String remark;        // 备注
	private String wxId;        // 微信号
	private String account;        // 微信账号
	private String nickNameStr;        // 昵称
	private Long sort;
	private Date beginCreateDate;        // 开始 创建时间
	private Date endCreateDate;        // 结束 创建时间
	private String tagId;
	private String openIds;        // openId
	private String newsId;

	private Integer count;//总数
	private Integer sex1;//男
	private Integer sex2;//女
	private Integer sex3;//未知
	private Integer language1;//简体中文
	private Integer language2;//繁体中文1
	private Integer language3;//繁体中文2
	private Integer language4;//英文
	private String province1;//省份

	public WxFans() {
		super();
	}

	public WxFans(String id) {
		super(id);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(String subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public byte[] getNickName() {
		return nickName;
	}

	public void setNickName(byte[] nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setNickNameStr(String nickNameStr) {
		this.nickNameStr = nickNameStr;
	}

	public String getNickNameStr() {
		if (this.getNickName() != null) {
			try {
				this.nickNameStr = new String(this.getNickName(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return nickNameStr;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}


	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSex1() {
		return sex1;
	}

	public void setSex1(Integer sex1) {
		this.sex1 = sex1;
	}

	public Integer getSex2() {
		return sex2;
	}

	public void setSex2(Integer sex2) {
		this.sex2 = sex2;
	}

	public Integer getSex3() {
		return sex3;
	}

	public void setSex3(Integer sex3) {
		this.sex3 = sex3;
	}

	public Integer getLanguage1() {
		return language1;
	}

	public void setLanguage1(Integer language1) {
		this.language1 = language1;
	}

	public Integer getLanguage2() {
		return language2;
	}

	public void setLanguage2(Integer language2) {
		this.language2 = language2;
	}

	public Integer getLanguage3() {
		return language3;
	}

	public void setLanguage3(Integer language3) {
		this.language3 = language3;
	}

	public Integer getLanguage4() {
		return language4;
	}

	public void setLanguage4(Integer language4) {
		this.language4 = language4;
	}

	public String getProvince1() {
		return province1;
	}

	public void setProvince1(String province1) {
		this.province1 = province1;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getOpenIds() {
		return openIds;
	}

	public void setOpenIds(String openIds) {
		this.openIds = openIds;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
}