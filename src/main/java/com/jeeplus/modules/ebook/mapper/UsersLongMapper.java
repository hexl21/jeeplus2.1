/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.ebook.entity.Users;
import org.apache.ibatis.annotations.Param;

/**
 * 管理用户MAPPER接口
 *
 * @author 高龙
 * @version 2019-01-12
 */
@MyBatisMapper
public interface UsersLongMapper extends BaseMapper<Users> {
    public Users selectOneUsers(@Param("username") String username, @Param("password") String password);

    public String selectOneUsersPassword(String password);

}