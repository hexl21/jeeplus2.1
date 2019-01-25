/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.ebook.entity.Enshrine;

import java.util.List;

/**
 * 管理收藏MAPPER接口
 *
 * @author 高龙
 * @version 2019-01-16
 */
@MyBatisMapper
public interface EnshrineLongMapper extends BaseMapper<Enshrine> {

    public List<Enshrine> selectAllEnshrine(String userid);

    public void daleteOneBookEnshrine(String id);

    public Enshrine selectOneEnshrine(String cid);
}