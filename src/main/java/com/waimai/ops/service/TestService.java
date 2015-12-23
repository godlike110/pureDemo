package com.waimai.ops.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waimai.ops.dao.AdminDao;
import com.waimai.ops.model.Admin;

@Service
public class TestService {

	@Autowired
     private AdminDao adminDao;
	
	
	public List<Admin> getAdmins(int offset,int limit) {
		return adminDao.getAdmins(offset, limit);
	}
	
	
}
