/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.ebook.entity.Buychapter;
import com.jeeplus.modules.ebook.mapper.BuychapterMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理购买章节Service
 *
 * @author 高龙
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class BuychapterService extends CrudService<BuychapterMapper, Buychapter> {

    public Buychapter get(String id) {
        return super.get(id);
    }

    public List<Buychapter> findList(Buychapter buychapter) {
        return super.findList(buychapter);
    }

    public Page<Buychapter> findPage(Page<Buychapter> page, Buychapter buychapter) {
        return super.findPage(page, buychapter);
    }

    @Transactional(readOnly = false)
    public void save(Buychapter buychapter) {
        super.save(buychapter);
    }

    @Transactional(readOnly = false)
    public void delete(Buychapter buychapter) {
        super.delete(buychapter);
    }

}