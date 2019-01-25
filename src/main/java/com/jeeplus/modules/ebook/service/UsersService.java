/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.ebook.entity.Users;
import com.jeeplus.modules.ebook.mapper.UsersMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理用户Service
 * @author 高龙
 * @version 2019-01-19
 */
@Service
@Transactional(readOnly = true)
public class UsersService extends CrudService<UsersMapper, Users> {

    public Users get(String id) {
        return super.get(id);
    }

    public List<Users> findList(Users users) {
        return super.findList(users);
    }

    public Page<Users> findPage(Page<Users> page, Users users) {
        return super.findPage(page, users);
    }

    @Transactional(readOnly = false)
    public void save(Users users) {
        super.save(users);
    }

    @Transactional(readOnly = false)
    public void delete(Users users) {
        super.delete(users);
    }
	
}