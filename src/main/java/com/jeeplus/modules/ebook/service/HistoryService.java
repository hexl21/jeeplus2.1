/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.ebook.entity.History;
import com.jeeplus.modules.ebook.mapper.HistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理历史Service
 * @author 高龙
 * @version 2019-01-15
 */
@Service
@Transactional(readOnly = true)
public class HistoryService extends CrudService<HistoryMapper, History> {

    public History get(String id) {
        return super.get(id);
    }

    public List<History> findList(History history) {
        return super.findList(history);
    }

    public Page<History> findPage(Page<History> page, History history) {
        return super.findPage(page, history);
    }

    @Transactional(readOnly = false)
    public void save(History history) {
        super.save(history);
    }

    @Transactional(readOnly = false)
    public void delete(History history) {
        super.delete(history);
    }
	
}