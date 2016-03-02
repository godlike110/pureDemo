package com.waimai.ops.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.sankuai.meituan.waimai.datasource.multi.annotation.DataSource;
import com.waimai.ops.model.Admin;


@Component
@DataSource("mydb")
public interface AdminDao {

	@Select("select * from admin where id = #{id}")
	public List<Admin> getAdmins(@Param("id") int id);

}
