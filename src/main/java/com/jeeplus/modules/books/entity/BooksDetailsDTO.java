package com.jeeplus.modules.books.entity;


import com.jeeplus.modules.book_chapter.entity.Chapter;

import java.io.Serializable;
import java.util.List;

public class BooksDetailsDTO implements Serializable {
    private Books books;
    private List<Chapter> chapterList;


    public BooksDetailsDTO() {
    }

    public BooksDetailsDTO(Books books, List<Chapter> chapterList) {
        this.books = books;
        this.chapterList = chapterList;
    }


    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    @Override
    public String toString() {
        return "BooksDetailsDTO{" +
                "books=" + books +
                ", chapterList=" + chapterList +
                '}';
    }

}
