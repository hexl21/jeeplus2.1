/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.books.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.book_category.entity.Category;

/**
 * 管理图书Entity
 * @author 高龙
 * @version 2019-01-04
 */
public class Books extends DataEntity<Books> {
	
	private static final long serialVersionUID = 1L;
	private String bookPic;		// 图书封面路径
	private String bookName;		// 图书名
	private Category category;		// 所属类别
	private String bookIntro;		// 简介
	private String bookContent;		// 内容
    private Integer bookReadnumber;        // 阅读人数
	private String state;		// 图书状态
	private String sex;		// 适合性别
    private String author;        // 作者
	
	public Books() {
		super();
	}

	public Books(String id){
		super(id);
	}

	@ExcelField(title="图书封面路径", align=2, sort=6)
	public String getBookPic() {
		return bookPic;
	}

	public void setBookPic(String bookPic) {
		this.bookPic = bookPic;
	}
	
	@ExcelField(title="图书名", align=2, sort=7)
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@ExcelField(title="所属类别", fieldType=Category.class, value="category.name", align=2, sort=8)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@ExcelField(title="简介", align=2, sort=9)
	public String getBookIntro() {
		return bookIntro;
	}

	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}
	
	@ExcelField(title="内容", align=2, sort=10)
	public String getBookContent() {
		return bookContent;
	}

	public void setBookContent(String bookContent) {
		this.bookContent = bookContent;
	}
	
	@ExcelField(title="阅读人数", align=2, sort=11)
    public Integer getBookReadnumber() {
		return bookReadnumber;
	}

    public void setBookReadnumber(Integer bookReadnumber) {
		this.bookReadnumber = bookReadnumber;
	}
	
	@ExcelField(title="图书状态", dictType="book_state", align=2, sort=12)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@ExcelField(title="适合性别", dictType="sex", align=2, sort=13)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

    @ExcelField(title = "作者", align = 2, sort = 15)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
	
}