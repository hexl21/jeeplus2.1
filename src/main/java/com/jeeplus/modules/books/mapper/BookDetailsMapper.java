package com.jeeplus.modules.books.mapper;

import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.books.entity.BooksDetailsDTO;
import com.jeeplus.modules.books.entity.DetailsDTO;


@MyBatisMapper
public interface BookDetailsMapper {
    public BooksDetailsDTO selectOneBookDetails(String id);

    public DetailsDTO selectOneBooksDetails(String id);

}
