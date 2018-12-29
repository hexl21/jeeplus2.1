/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.product.entity.Testproduct;
import com.jeeplus.modules.product.mapper.TestproductMapper;
import com.jeeplus.modules.product.entity.Testproductchild;
import com.jeeplus.modules.product.mapper.TestproductchildMapper;

/**
 * 商品的Service
 * @author 某人2
 * @version 2018-12-26
 */
@Service
@Transactional(readOnly = true)
public class TestproductService extends CrudService<TestproductMapper, Testproduct> {

	@Autowired
	private TestproductchildMapper testproductchildMapper;
	
	public Testproduct get(String id) {
		Testproduct testproduct = super.get(id);
		testproduct.setTestproductchildList(testproductchildMapper.findList(new Testproductchild(testproduct)));
		return testproduct;
	}
	
	public List<Testproduct> findList(Testproduct testproduct) {
		return super.findList(testproduct);
	}
	
	public Page<Testproduct> findPage(Page<Testproduct> page, Testproduct testproduct) {
		return super.findPage(page, testproduct);
	}
	
	@Transactional(readOnly = false)
	public void save(Testproduct testproduct) {
		super.save(testproduct);
		for (Testproductchild testproductchild : testproduct.getTestproductchildList()){
			if (testproductchild.getId() == null){
				continue;
			}
			if (Testproductchild.DEL_FLAG_NORMAL.equals(testproductchild.getDelFlag())){
				if (StringUtils.isBlank(testproductchild.getId())){
					testproductchild.setProductmain(testproduct);
					testproductchild.preInsert();
					testproductchildMapper.insert(testproductchild);
				}else{
					testproductchild.preUpdate();
					testproductchildMapper.update(testproductchild);
				}
			}else{
				testproductchildMapper.delete(testproductchild);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Testproduct testproduct) {
		super.delete(testproduct);
		testproductchildMapper.delete(new Testproductchild(testproduct));
	}
	
}