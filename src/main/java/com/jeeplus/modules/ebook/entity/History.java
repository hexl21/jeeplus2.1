/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 管理历史Entity
 * @author 高龙
 * @version 2019-01-15
 */
public class History extends DataEntity<History> {

    private static final long serialVersionUID = 1L;
    private String bookname;        // 书名
    private String bookid;        // 图书ID
    private String bookpic;        // 图书封面
    private String chaptername;        // 章节名
    private String chapterid;        // 章节ID
    private String userid;        // 用户ID
    private Date date;        // 当前系统时间

    public History() {
        super();
    }

    public History(String id) {
        super(id);
    }

    @ExcelField(title = "书名", align = 2, sort = 6)
    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    @ExcelField(title = "图书ID", align = 2, sort = 7)
    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    @ExcelField(title = "图书封面", align = 2, sort = 8)
    public String getBookpic() {
        return bookpic;
    }

    public void setBookpic(String bookpic) {
        this.bookpic = bookpic;
    }

    @ExcelField(title = "章节名", align = 2, sort = 9)
    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    @ExcelField(title = "章节ID", align = 2, sort = 10)
    public String getChapterid() {
        return chapterid;
    }

    public void setChapterid(String chapterid) {
        this.chapterid = chapterid;
    }

    @ExcelField(title = "用户ID", align = 2, sort = 11)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "当前系统时间", align = 2, sort = 12)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
	
}