/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.weixin.entity.WxMsgBaseSub;
import com.jeeplus.modules.weixin.mapper.WxMsgBaseSubMapper;

/**
 * 微信消息Service
 * @author toteny
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class WxMsgBaseSubService extends CrudService<WxMsgBaseSubMapper, WxMsgBaseSub> {

	public WxMsgBaseSub get(String id) {
		return super.get(id);
	}
	
	public List<WxMsgBaseSub> findList(WxMsgBaseSub wxMsgBaseSub) {
		return super.findList(wxMsgBaseSub);
	}
	
	public Page<WxMsgBaseSub> findPage(Page<WxMsgBaseSub> page, WxMsgBaseSub wxMsgBaseSub) {
		return super.findPage(page, wxMsgBaseSub);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMsgBaseSub wxMsgBaseSub) {
		super.save(wxMsgBaseSub);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMsgBaseSub wxMsgBaseSub) {
		super.delete(wxMsgBaseSub);
	}
	
}