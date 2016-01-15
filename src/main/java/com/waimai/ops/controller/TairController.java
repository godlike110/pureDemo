package com.waimai.ops.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.handler.codec.spdy.SpdySettingsFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String getValue(@RequestParam(value="key",required=false) String key,@RequestParam(value="env",required=false) String env,Model model) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(env)) {
			return "tairget";
		} else {
			String value = tairService.getValue(env, key);
			model.addAttribute("env", env);
			model.addAttribute("key", key);
			model.addAttribute("value", value);
			return "tairget";
		}
	}
	
	@RequestMapping("set")
	public String setValue(@RequestParam(value="key",required=false) String key,@RequestParam(value="env",required=false) String env,@RequestParam(value="value",required=false) String value,Model model) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(env) || StringUtils.isBlank(value)) {
			return "tairset";
		} else {
			boolean result = tairService.setValue(env, key, value);
			model.addAttribute("result", result);
			return "tairset";
		}
	}
	
	
}
