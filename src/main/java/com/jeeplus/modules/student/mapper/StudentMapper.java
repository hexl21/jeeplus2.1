/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.student.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.student.entity.Student;

/**
 * 管理学生MAPPER接口
 * @author 高龙
 * @version 2018-12-29
 */
@MyBatisMapper
public interface StudentMapper extends BaseMapper<Student> {
	
}