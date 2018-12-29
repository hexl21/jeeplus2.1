/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tree.tree.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.tree.tree.entity.Treee;
import com.jeeplus.modules.tree.tree.mapper.TreeeMapper;

/**
 * 树的测试Service
 * @author 某人3
 * @version 2018-12-27
 */
@Service
@Transactional(readOnly = true)
public class TreeeService extends TreeService<TreeeMapper, Treee> {

	public Treee get(String id) {
		return super.get(id);
	}
	
	public List<Treee> findList(Treee treee) {
		if (StringUtils.isNotBlank(treee.getParentIds())){
			treee.setParentIds(","+treee.getParentIds()+",");
		}
		return super.findList(treee);
	}
	
	@Transactional(readOnly = false)
	public void save(Treee treee) {
		super.save(treee);
	}
	
	@Transactional(readOnly = false)
	public void delete(Treee treee) {
		super.delete(treee);
	}
	
}