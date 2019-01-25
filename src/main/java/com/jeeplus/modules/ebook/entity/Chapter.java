/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.books.entity.Books;

/**
 * 管理章节Entity
 * @author 高龙
 * @version 2019-01-24
 */
public class Chapter extends DataEntity<Chapter> {

	private static final long serialVersionUID = 1L;
	private Books books;        // 所属图书ID
	private String name;        // 章节名
	private String content;        // 内容
	private String sectionpath;        // 章节路径
	private String charge;        // 是否付费
	private String money;        // 书币

	public Chapter() {
		super();
	}

	public Chapter(String id) {
		super(id);
	}

	@ExcelField(title = "所属图书ID", fieldType = Books.class, value = "books.bookName", align = 2, sort = 6)
	public Books getBooks() {
		return books;
	}

	public void setBooks(Books books) {
		this.books = books;
	}

	@ExcelField(title = "章节名", align = 2, sort = 7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title = "内容", align = 2, sort = 8)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ExcelField(title = "章节路径", align = 2, sort = 9)
	public String getSectionpath() {
		return sectionpath;
	}

	public void setSectionpath(String sectionpath) {
		this.sectionpath = sectionpath;
	}

	@ExcelField(title = "是否付费", dictType = "charge", align = 2, sort = 10)
	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	@ExcelField(title = "书币", align = 2, sort = 11)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
}