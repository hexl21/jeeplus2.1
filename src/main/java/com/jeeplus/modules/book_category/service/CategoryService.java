/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.book_category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.book_category.entity.Category;
import com.jeeplus.modules.book_category.mapper.CategoryMapper;

/**
 * 管理类别Service
 * @author 高龙
 * @version 2019-01-04
 */
@Service
@Transactional(readOnly = true)
public class CategoryService extends CrudService<CategoryMapper, Category> {

	public Category get(String id) {
		return super.get(id);
	}
	
	public List<Category> findList(Category category) {
		return super.findList(category);
	}
	
	public Page<Category> findPage(Page<Category> page, Category category) {
		return super.findPage(page, category);
	}
	
	@Transactional(readOnly = false)
	public void save(Category category) {
		super.save(category);
	}
	
	@Transactional(readOnly = false)
	public void delete(Category category) {
		super.delete(category);
	}
	
}