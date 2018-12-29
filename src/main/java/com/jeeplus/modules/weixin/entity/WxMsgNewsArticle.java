/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 图文信息Entity
 * @author toteny
 * @version 2018-07-14
 */
public class WxMsgNewsArticle extends DataEntity<WxMsgNewsArticle> {
	
	private static final long serialVersionUID = 1L;
	private String multType;		// 单图文多图文类型
	private String title;		// 标题
	private String author;		// 作者
	private String brief;		// 简介
	private String content;		// 描述
	private String picPath;		// 封面图片
	private String picDir;		// 封面图片绝对目录
	private String showPic;		// 是否显示图片
	private String url;		// 图文消息原文链接
	private String fromUrl;		// 外部链接
	private String mediaId;		// 上传后返回的媒体素材id
	private String thumbMediaId;		// 封面图片id
	private Long newsIndex;		// 多图文中的第几条
	private String account;		// account
	private String newsId;		// news_id
	private String status;        // 是否发布
	
	public WxMsgNewsArticle() {
		super();
	}

	public WxMsgNewsArticle(String id){
		super(id);
	}

	@ExcelField(title="单图文多图文类型", align=2, sort=1)
	public String getMultType() {
		return multType;
	}

	public void setMultType(String multType) {
		this.multType = multType;
	}
	
	@ExcelField(title="标题", align=2, sort=2)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="作者", align=2, sort=3)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@ExcelField(title="简介", align=2, sort=4)
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	@ExcelField(title="描述", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="封面图片", align=2, sort=6)
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	@ExcelField(title="封面图片绝对目录", align=2, sort=7)
	public String getPicDir() {
		return picDir;
	}

	public void setPicDir(String picDir) {
		this.picDir = picDir;
	}
	
	@ExcelField(title="是否显示图片", dictType="yes_no", align=2, sort=8)
	public String getShowPic() {
		return showPic;
	}

	public void setShowPic(String showPic) {
		this.showPic = showPic;
	}
	
	@ExcelField(title="图文消息原文链接", align=2, sort=9)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="外部链接", align=2, sort=10)
	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}
	
	@ExcelField(title="上传后返回的媒体素材id", align=2, sort=11)
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@ExcelField(title="封面图片id", align=2, sort=12)
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	@ExcelField(title="多图文中的第几条", align=2, sort=13)
	public Long getNewsIndex() {
		return newsIndex;
	}

	public void setNewsIndex(Long newsIndex) {
		this.newsIndex = newsIndex;
	}
	
	@ExcelField(title="account", align=2, sort=14)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@ExcelField(title="news_id", align=2, sort=16)
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}