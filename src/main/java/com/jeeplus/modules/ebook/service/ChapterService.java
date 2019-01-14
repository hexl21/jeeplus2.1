/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.ebook.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.ebook.entity.Chapter;
import com.jeeplus.modules.ebook.mapper.ChapterMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理章节Service
 *
 * @author 高龙
 * @version 2019-01-12
 */
@Service
@Transactional(readOnly = true)
public class ChapterService extends CrudService<ChapterMapper, Chapter> {

    public Chapter get(String id) {
        return super.get(id);
    }

    public List<Chapter> findList(Chapter chapter) {
        return super.findList(chapter);
    }

    public Page<Chapter> findPage(Page<Chapter> page, Chapter chapter) {
        return super.findPage(page, chapter);
    }

    @Transactional(readOnly = false)
    public void save(Chapter chapter) {
        super.save(chapter);
    }

    @Transactional(readOnly = false)
    public void delete(Chapter chapter) {
        super.delete(chapter);
    }

}