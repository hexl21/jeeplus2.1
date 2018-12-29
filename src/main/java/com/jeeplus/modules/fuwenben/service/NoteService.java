/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fuwenben.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.fuwenben.entity.Note;
import com.jeeplus.modules.fuwenben.mapper.NoteMapper;

/**
 * 富文本Service
 * @author 某人4
 * @version 2018-12-27
 */
@Service
@Transactional(readOnly = true)
public class NoteService extends CrudService<NoteMapper, Note> {

	public Note get(String id) {
		return super.get(id);
	}
	
	public List<Note> findList(Note note) {
		return super.findList(note);
	}
	
	public Page<Note> findPage(Page<Note> page, Note note) {
		return super.findPage(page, note);
	}
	
	@Transactional(readOnly = false)
	public void save(Note note) {
		super.save(note);
	}
	
	@Transactional(readOnly = false)
	public void delete(Note note) {
		super.delete(note);
	}
	
}