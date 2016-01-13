package com.waimai.ops.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.handler.codec.spdy.SpdySettingsFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.waimai.ops.service.TairService;

@Controller
@RequestMapping("tair")
public class TairController {

	public static Logger logger = LoggerFactory.getLogger(TairController.class);
	
	@Autowired
	private TairService tairService;
	
	@RequestMapping("find")
	public ModelAndView getValue(@RequestParam(value="key",required=false) String key,@RequestParam(value="env",required=false) String env) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(env)) {
			return new ModelAndView("tairget", null);
		} else {
			String value = tairService.getValue(env, key);
			Map<String, String> result = new HashMap<>();
			result.put("env", env);
			result.put("key", key);
			result.put("value", value);
			return new ModelAndView("tairget", result);
		}
	}
	
	@RequestMapping("set")
	public ModelAndView setValue(@RequestParam(value="key",required=false) String key,@RequestParam(value="env",required=false) String env,@RequestParam(value="value",required=false) String value) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(env) || StringUtils.isBlank(value)) {
			return new ModelAndView("tairset", null);
		} else {
			boolean result = tairService.setValue(env, key, value);
			Map<String, String> rMap = new HashMap<>();
			rMap.put("result", String.valueOf(result));
			return new ModelAndView("tairset", rMap);
		}
	}
	
	
}
