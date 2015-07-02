package com.qunar.ops.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qunar.ops.model.JsonDateSerializer;
public class Admin implements Serializable {

	/**
	 * @Author zhiwei.wen
	 * @Date 2015年7月2日下午3:50:57
	 */
	private static long serialVersionUID = -2356859755444087901L;

	private int id;
	
	private String name;
	
	private String phone;
	
	private Date createTime;

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
}
