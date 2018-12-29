/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.studentcourse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.studentcourse.entity.StudentCourse;
import com.jeeplus.modules.studentcourse.mapper.StudentCourseMapper;

/**
 * 学生的记录表Service
 * @author 某人10
 * @version 2018-12-27
 */
@Service
@Transactional(readOnly = true)
public class StudentCourseService extends CrudService<StudentCourseMapper, StudentCourse> {

	public StudentCourse get(String id) {
		return super.get(id);
	}
	
	public List<StudentCourse> findList(StudentCourse studentCourse) {
		return super.findList(studentCourse);
	}
	
	public Page<StudentCourse> findPage(Page<StudentCourse> page, StudentCourse studentCourse) {
		return super.findPage(page, studentCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentCourse studentCourse) {
		super.save(studentCourse);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentCourse studentCourse) {
		super.delete(studentCourse);
	}
	
}