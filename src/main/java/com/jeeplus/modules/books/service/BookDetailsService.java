package com.jeeplus.modules.books.service;


import com.jeeplus.modules.book_chapter.entity.Chapter;
import com.jeeplus.modules.books.entity.BooksDetailsDTO;
import com.jeeplus.modules.books.entity.DetailsDTO;


public interface BookDetailsService {
    public BooksDetailsDTO selectOneBookDetails(String id);

    public DetailsDTO selectOneBooksDetails(String id);

    public Chapter selectOneChapter(String id);
}
