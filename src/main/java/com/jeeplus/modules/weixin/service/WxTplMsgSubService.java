/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.weixin.entity.WxTplMsgSub;
import com.jeeplus.modules.weixin.mapper.WxTplMsgSubMapper;

/**
 * 模板消息Service
 * @author toteny
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class WxTplMsgSubService extends CrudService<WxTplMsgSubMapper, WxTplMsgSub> {

	public WxTplMsgSub get(String id) {
		return super.get(id);
	}
	
	public List<WxTplMsgSub> findList(WxTplMsgSub wxTplMsgSub) {
		return super.findList(wxTplMsgSub);
	}
	
	public Page<WxTplMsgSub> findPage(Page<WxTplMsgSub> page, WxTplMsgSub wxTplMsgSub) {
		return super.findPage(page, wxTplMsgSub);
	}
	
	@Transactional(readOnly = false)
	public void save(WxTplMsgSub wxTplMsgSub) {
		super.save(wxTplMsgSub);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxTplMsgSub wxTplMsgSub) {
		super.delete(wxTplMsgSub);
	}
	
}