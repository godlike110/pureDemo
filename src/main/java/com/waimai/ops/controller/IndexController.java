package com.waimai.ops.controller;

import java.awt.print.Printable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.waimai.ops.annotation.AccessLoginRequired;
import com.waimai.ops.model.Admin;
import com.waimai.ops.model.User;
import com.waimai.ops.service.TestService;

@AccessLoginRequired
@Controller
@RequestMapping("/api/")
public class IndexController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private TestService testService;

	@RequestMapping("index")
	public ModelAndView index(@RequestParam("a") int a, @RequestParam("b") int b) {

		logger.info("requestId:{}", this.getAccessData().getCurrentRequestId());
		return new ModelAndView("index");
	}

	@RequestMapping("print")
	@ResponseBody
	public String myprint(User user) {
		JSONObject jObject = new JSONObject();
		jObject.put("a", 100);
		jObject.put("c", "hello worold");
		return JSONObject.toJSONString(jObject);
	}

	@RequestMapping("list")
	@ResponseBody
	public String getList(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
		List<Admin> admins = testService.getAdmins(offset, limit);
		return JSONObject.toJSONString(admins);
	}
}
