/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.ebook.entity.History;

import java.util.List;

/**
 * 管理历史MAPPER接口
 *
 * @author 高龙
 * @version 2019-01-12
 */
@MyBatisMapper
public interface HistoryLongMapper extends BaseMapper<History> {
    public Books selectOneBooks(String id);

    public List<History> selectAllHistory(String userid);

    public void daleteOneBookHistory(String id);


}