/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cms.entity.Comment;
import com.jeeplus.modules.cms.mapper.CommentMapper;

/**
 * 评论信息Service
 * @author toteny
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentMapper, Comment> {

	public Comment get(String id) {
		return super.get(id);
	}
	
	public List<Comment> findList(Comment comment) {
		return super.findList(comment);
	}
	
	public Page<Comment> findPage(Page<Comment> page, Comment comment) {
		return super.findPage(page, comment);
	}
	
	@Transactional(readOnly = false)
	public void save(Comment comment) {
		super.save(comment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Comment comment) {
		super.delete(comment);
	}
	
}