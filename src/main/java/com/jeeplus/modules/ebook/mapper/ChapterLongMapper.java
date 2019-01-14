/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.ebook.entity.Chapter;

/**
 * 管理章节MAPPER接口
 *
 * @author 高龙
 * @version 2019-01-12
 */
@MyBatisMapper
public interface ChapterLongMapper extends BaseMapper<Chapter> {
    Chapter selectOneChapter(String id);

}