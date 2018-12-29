/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.contry.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.contry.entity.TestContry;
import com.jeeplus.modules.contry.mapper.TestContryMapper;

/**
 * 国家Service
 * @author 某人7
 * @version 2018-12-27
 */
@Service
@Transactional(readOnly = true)
public class TestContryService extends CrudService<TestContryMapper, TestContry> {

	public TestContry get(String id) {
		return super.get(id);
	}
	
	public List<TestContry> findList(TestContry testContry) {
		return super.findList(testContry);
	}
	
	public Page<TestContry> findPage(Page<TestContry> page, TestContry testContry) {
		return super.findPage(page, testContry);
	}
	
	@Transactional(readOnly = false)
	public void save(TestContry testContry) {
		super.save(testContry);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestContry testContry) {
		super.delete(testContry);
	}
	
}