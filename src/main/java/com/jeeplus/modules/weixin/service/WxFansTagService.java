/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.weixin.entity.WxFansTag;
import com.jeeplus.modules.weixin.mapper.WxFansTagMapper;

/**
 * 用户标签Service
 * @author toteny
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class WxFansTagService extends CrudService<WxFansTagMapper, WxFansTag> {

	public WxFansTag get(String id) {
		return super.get(id);
	}
	
	public List<WxFansTag> findList(WxFansTag wxFansTag) {
		return super.findList(wxFansTag);
	}
	
	public Page<WxFansTag> findPage(Page<WxFansTag> page, WxFansTag wxFansTag) {
		return super.findPage(page, wxFansTag);
	}
	
	@Transactional(readOnly = false)
	public void save(WxFansTag wxFansTag) {
		super.save(wxFansTag);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxFansTag wxFansTag) {
		super.delete(wxFansTag);
	}

    public WxFansTag getName(WxFansTag wxFansTag) {
		return mapper.getName(wxFansTag);
    }
}