/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.entity;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 管理收藏Entity
 *
 * @author 高龙
 * @version 2019-01-12
 */
public class Enshrine extends DataEntity<Enshrine> {

    private static final long serialVersionUID = 1L;
    private String bookid;        // 图书ID
    private String bookname;        // 书名
    private String bookpic;        // 图书封面
    private String chapterid;        // 章节ID
    private String userid;        // 用户ID

    public Enshrine() {
        super();
    }

    public Enshrine(String id) {
        super(id);
    }

    @ExcelField(title = "图书ID", align = 2, sort = 6)
    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    @ExcelField(title = "书名", align = 2, sort = 7)
    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    @ExcelField(title = "图书封面", align = 2, sort = 8)
    public String getBookpic() {
        return bookpic;
    }

    public void setBookpic(String bookpic) {
        this.bookpic = bookpic;
    }

    @ExcelField(title = "章节ID", align = 2, sort = 9)
    public String getChapterid() {
        return chapterid;
    }

    public void setChapterid(String chapterid) {
        this.chapterid = chapterid;
    }

    @ExcelField(title = "用户ID", align = 2, sort = 10)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}