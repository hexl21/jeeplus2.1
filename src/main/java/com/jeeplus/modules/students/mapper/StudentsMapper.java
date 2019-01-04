/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.students.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.students.entity.Students;

/**
 * 管理学生MAPPER接口
 * @author 高龙
 * @version 2019-01-03
 */
@MyBatisMapper
public interface StudentsMapper extends BaseMapper<Students> {
	
}