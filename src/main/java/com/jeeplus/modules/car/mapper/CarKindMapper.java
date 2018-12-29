/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.car.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.car.entity.CarKind;

/**
 * 车系MAPPER接口
 * @author 某人11
 * @version 2018-12-27
 */
@MyBatisMapper
public interface CarKindMapper extends TreeMapper<CarKind> {
	
}