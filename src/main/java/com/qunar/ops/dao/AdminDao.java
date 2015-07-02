package com.qunar.ops.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qunar.ops.model.Admin;

public interface AdminDao {


	List<Admin> getAdmins(@Param("offset") int offset,@Param("limit") int limit);
}
