/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.baizhi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.baizhi.entity.Baizhi;
import com.jeeplus.modules.baizhi.mapper.BaizhiMapper;

/**
 * 这是一个测试类Service
 * @author 某人
 * @version 2018-12-27
 */
@Service
@Transactional(readOnly = true)
public class BaizhiService extends CrudService<BaizhiMapper, Baizhi> {

	public Baizhi get(String id) {
		return super.get(id);
	}
	
	public List<Baizhi> findList(Baizhi baizhi) {
		return super.findList(baizhi);
	}
	
	public Page<Baizhi> findPage(Page<Baizhi> page, Baizhi baizhi) {
		return super.findPage(page, baizhi);
	}
	
	@Transactional(readOnly = false)
	public void save(Baizhi baizhi) {
		super.save(baizhi);
	}
	
	@Transactional(readOnly = false)
	public void delete(Baizhi baizhi) {
		super.delete(baizhi);
	}
	
}