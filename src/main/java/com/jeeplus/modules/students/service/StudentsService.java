/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.students.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.students.entity.Students;
import com.jeeplus.modules.students.mapper.StudentsMapper;

/**
 * 管理学生Service
 * @author 高龙
 * @version 2019-01-03
 */
@Service
@Transactional(readOnly = true)
public class StudentsService extends CrudService<StudentsMapper, Students> {

	public Students get(String id) {
		return super.get(id);
	}
	
	public List<Students> findList(Students students) {
		return super.findList(students);
	}
	
	public Page<Students> findPage(Page<Students> page, Students students) {
		return super.findPage(page, students);
	}
	
	@Transactional(readOnly = false)
	public void save(Students students) {
		super.save(students);
	}
	
	@Transactional(readOnly = false)
	public void delete(Students students) {
		super.delete(students);
	}
	
}