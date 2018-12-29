/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.weixin.entity.WxFansTagMpping;
import com.jeeplus.modules.weixin.mapper.WxFansTagMppingMapper;

/**
 * 微信用户标签Service
 *
 * @author toteny
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class WxFansTagMppingService extends CrudService<WxFansTagMppingMapper, WxFansTagMpping> {

    public WxFansTagMpping get(String id) {
        return super.get(id);
    }

    public List<WxFansTagMpping> findList(WxFansTagMpping wxFansTagMpping) {
        return super.findList(wxFansTagMpping);
    }

    public Page<WxFansTagMpping> findPage(Page<WxFansTagMpping> page, WxFansTagMpping wxFansTagMpping) {
        return super.findPage(page, wxFansTagMpping);
    }

    @Transactional(readOnly = false)
    public void save(WxFansTagMpping wxFansTagMpping) {
        super.save(wxFansTagMpping);
    }

    @Transactional(readOnly = false)
    public void delete(WxFansTagMpping wxFansTagMpping) {
        super.delete(wxFansTagMpping);
    }

    public WxFansTagMpping getMapping(WxFansTagMpping wxFansTagMpping) {
        return mapper.getMapping(wxFansTagMpping);
    }
}