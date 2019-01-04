/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.books.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.books.mapper.BooksMapper;

/**
 * 管理图书Service
 * @author 高龙
 * @version 2019-01-04
 */
@Service
@Transactional(readOnly = true)
public class BooksService extends CrudService<BooksMapper, Books> {

	public Books get(String id) {
		return super.get(id);
	}
	
	public List<Books> findList(Books books) {
		return super.findList(books);
	}
	
	public Page<Books> findPage(Page<Books> page, Books books) {
		return super.findPage(page, books);
	}
	
	@Transactional(readOnly = false)
	public void save(Books books) {
		super.save(books);
	}
	
	@Transactional(readOnly = false)
	public void delete(Books books) {
		super.delete(books);
	}
	
}