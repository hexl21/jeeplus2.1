package com.jeeplus.modules.books.entity;

import com.jeeplus.modules.book_chapter.entity.Chapter;

import java.io.Serializable;
import java.util.List;

public class DetailsDTO implements Serializable {
    private String id;        // 图书id
    private String cid;        // 类别id
    private String cname;        // 类别名
    private String bookPic;        // 图书封面路径
    private String bookName;        // 图书名
    private String bookIntro;        // 简介
    private String bookContent;        // 内容
    private Integer bookReadnumber;        // 阅读人数
    private String state;        // 图书状态
    private String sex;        // 适合性别
    private String author;        // 作者
    private List<Chapter> chapterLists;
//    private String chid;		// 章节ID
//    private String chname;		// 章节名
//    private String content;		// 内容
//    private String chpath;		// 章节路径
//    private String charge;		// 是否付费

    public DetailsDTO() {
    }

    public DetailsDTO(String id, String cid, String cname, String bookPic, String bookName, String bookIntro, String bookContent, Integer bookReadnumber, String state, String sex, String author, List<Chapter> chapterLists) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
        this.bookPic = bookPic;
        this.bookName = bookName;
        this.bookIntro = bookIntro;
        this.bookContent = bookContent;
        this.bookReadnumber = bookReadnumber;
        this.state = state;
        this.sex = sex;
        this.author = author;
        this.chapterLists = chapterLists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }

    public Integer getBookReadnumber() {
        return bookReadnumber;
    }

    public void setBookReadnumber(Integer bookReadnumber) {
        this.bookReadnumber = bookReadnumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Chapter> getChapterLists() {
        return chapterLists;
    }

    public void setChapterLists(List<Chapter> chapterLists) {
        this.chapterLists = chapterLists;
    }

    @Override
    public String toString() {
        return "DetailsDTO{" +
                "id='" + id + '\'' +
                ", cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                ", bookPic='" + bookPic + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookIntro='" + bookIntro + '\'' +
                ", bookContent='" + bookContent + '\'' +
                ", bookReadnumber=" + bookReadnumber +
                ", state='" + state + '\'' +
                ", sex='" + sex + '\'' +
                ", author='" + author + '\'' +
                ", chapterLists=" + chapterLists +
                '}';
    }
}
