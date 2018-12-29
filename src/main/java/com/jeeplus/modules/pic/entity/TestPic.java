/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.pic.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 上传的图片Entity
 * @author 某人5
 * @version 2018-12-27
 */
public class TestPic extends DataEntity<TestPic> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String pic;		// 图片路径
	
	public TestPic() {
		super();
	}

	public TestPic(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=7)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="图片路径", align=2, sort=8)
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
}