/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.ebook.entity.Enshrine;
import com.jeeplus.modules.ebook.mapper.EnshrineMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理收藏Service
 * @author 高龙
 * @version 2019-01-16
 */
@Service
@Transactional(readOnly = true)
public class EnshrineService extends CrudService<EnshrineMapper, Enshrine> {

    public Enshrine get(String id) {
        return super.get(id);
    }

    public List<Enshrine> findList(Enshrine enshrine) {
        return super.findList(enshrine);
    }

    public Page<Enshrine> findPage(Page<Enshrine> page, Enshrine enshrine) {
        return super.findPage(page, enshrine);
    }

    @Transactional(readOnly = false)
    public void save(Enshrine enshrine) {
        super.save(enshrine);
    }

    @Transactional(readOnly = false)
    public void delete(Enshrine enshrine) {
        super.delete(enshrine);
    }
	
}