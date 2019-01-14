package com.jeeplus.modules.books.service.impl;

import com.jeeplus.modules.books.entity.BooksDetailsDTO;
import com.jeeplus.modules.books.entity.DetailsDTO;
import com.jeeplus.modules.books.mapper.BookDetailsMapper;
import com.jeeplus.modules.books.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    @Autowired
    private BookDetailsMapper bookDetailsMapper;

    @Override
    public BooksDetailsDTO selectOneBookDetails(String id) {
        BooksDetailsDTO booksDetailsDTO = bookDetailsMapper.selectOneBookDetails(id);
        return booksDetailsDTO;
    }

    @Override
    public DetailsDTO selectOneBooksDetails(String id) {
        return bookDetailsMapper.selectOneBooksDetails(id);
    }

}
