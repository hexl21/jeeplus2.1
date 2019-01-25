/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.entity;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 管理购买章节Entity
 *
 * @author 高龙
 * @version 2019-01-24
 */
public class Buychapter extends DataEntity<Buychapter> {

    private static final long serialVersionUID = 1L;
    private String userid;        // 用户ID
    private String bookname;        // 书名
    private String chapterid;        // 章节ID
    private String chaptername;        // 章节名
    private String chaptermoney;        // 章节书币

    public Buychapter() {
        super();
    }

    public Buychapter(String id) {
        super(id);
    }

    @ExcelField(title = "用户ID", align = 2, sort = 6)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @ExcelField(title = "书名", align = 2, sort = 7)
    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    @ExcelField(title = "章节ID", align = 2, sort = 8)
    public String getChapterid() {
        return chapterid;
    }

    public void setChapterid(String chapterid) {
        this.chapterid = chapterid;
    }

    @ExcelField(title = "章节名", align = 2, sort = 9)
    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    @ExcelField(title = "章节书币", align = 2, sort = 10)
    public String getChaptermoney() {
        return chaptermoney;
    }

    public void setChaptermoney(String chaptermoney) {
        this.chaptermoney = chaptermoney;
    }

}