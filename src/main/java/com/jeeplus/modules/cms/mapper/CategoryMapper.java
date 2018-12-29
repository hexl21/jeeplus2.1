/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cms.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.cms.entity.Category;

/**
 * 栏目信息MAPPER接口
 * @author toteny
 * @version 2018-07-20
 */
@MyBatisMapper
public interface CategoryMapper extends TreeMapper<Category> {
	
}