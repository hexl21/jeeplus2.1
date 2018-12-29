/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 评论信息Entity
 * @author toteny
 * @version 2018-07-14
 */
public class Comment extends DataEntity<Comment> {
	
	private static final long serialVersionUID = 1L;
	private String contentId;		// 栏目内容的编号
	private String content;		// 评论内容
	private String memberId;		// 前台用户id
	private String auditUserId;		// 审核人
	private Date auditDate;		// 审核时间
	
	public Comment() {
		super();
	}

	public Comment(String id){
		super(id);
	}

	@ExcelField(title="栏目内容的编号", align=2, sort=1)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@ExcelField(title="评论内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="前台用户id", align=2, sort=3)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@ExcelField(title="审核人", align=2, sort=5)
	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=6)
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
}