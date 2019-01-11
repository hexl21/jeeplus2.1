package com.jeeplus.modules.books.mapper;

import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.book_chapter.entity.Chapter;
import com.jeeplus.modules.books.entity.BooksDetailsDTO;
import com.jeeplus.modules.books.entity.DetailsDTO;


@MyBatisMapper
public interface BookDetailsMapper {
    public BooksDetailsDTO selectOneBookDetails(String id);

    public DetailsDTO selectOneBooksDetails(String id);

    public Chapter selectOneChapter(String id);
}
