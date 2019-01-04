/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.students.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 管理学生Entity
 * @author 高龙
 * @version 2019-01-03
 */
public class Students extends DataEntity<Students> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// 学号
	private String name;		// 姓名
	private String sex;		// 性别
	private String age;		// 年龄
	private String grade;		// 年级
	private String teacher;		// 老师
	private String score;		// 成绩
	private String place;		// 名次
	
	public Students() {
		super();
	}

	public Students(String id){
		super(id);
	}

	@ExcelField(title="学号", align=2, sort=7)
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@ExcelField(title="姓名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="性别", align=2, sort=9)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="年龄", align=2, sort=10)
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@ExcelField(title="年级", align=2, sort=11)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@ExcelField(title="老师", align=2, sort=12)
	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	@ExcelField(title="成绩", align=2, sort=13)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@ExcelField(title="名次", align=2, sort=14)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}