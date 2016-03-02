package com.waimai.ops.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.waimai.ops.model.Admin;
import com.waimai.ops.service.TestService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring.xml")
public class TestServiceTest {
	
	@Autowired
	private TestService testService;
	
	@Test
	public void testService() {
		List<Admin> admins = testService.getAdmins(0, 0);
		System.out.println(JSONObject.toJSONString(admins));
	}
 
}
