/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.books.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.books.entity.Books;
import com.jeeplus.modules.books.entity.ConditionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理图书MAPPER接口
 *
 * @author 高龙
 * @version 2019-01-04
 */
@MyBatisMapper
public interface BookMapper extends BaseMapper<Books> {
    //轮播图
    public List<Books> slideshowBooks();


    //主编推荐
    public List<Books> recommendBooks();

    //新书上架
    public List<Books> newBooks();

    //连载精品
    public List<Books> serialBooks();

    //完结精品
    public List<Books> completionBooks();

    //(书库)条件查询
    public List<Books> selectConditionBooks(ConditionDTO conditionDTO);


    //(搜索)条件查询
    public List<Books> seekBooks(@Param("name") String name);

    //热门搜索
    public List<Books> popularityBooks();
}