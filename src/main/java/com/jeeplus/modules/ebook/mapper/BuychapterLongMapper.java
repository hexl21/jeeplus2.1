/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.ebook.entity.Buychapter;
import com.jeeplus.modules.ebook.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理购买章节MAPPER接口
 *
 * @author 高龙
 * @version 2019-01-19
 */
@MyBatisMapper
public interface BuychapterLongMapper extends BaseMapper<Buychapter> {

    public String selectOneBuychapter(@Param("chapterid") String chapterid, @Param("userid") String userid);

    public void updateUsersMoney(@Param("money") String money, @Param("userid") String userid);


    Chapter selectMoney(String chapterid);

    List<Buychapter> selectAllBuychapter();

}