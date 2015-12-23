package com.waimai.ops.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.waimai.ops.aptconfig.AptConstants;


@Controller
@RequestMapping("admin")
public class AdminController {

	public static Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@RequestMapping("addip")
	public ModelAndView addIp(@RequestParam(value="ip",required=false) String ip) {

		Map<String, Object> msg = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(ip)) {
			msg.put("msg", "ip is null");
		}
		
		if(AptConstants.IP_LIST.contains(ip)) {
			msg.put("msg", "ip is already exsit");
		}
		
		AptConstants.IP_LIST = AptConstants.IP_LIST + ";" +ip;
		msg.put("msg", "ip is add to the whilt list");
		return new ModelAndView("info", msg);
		
	}
	
}
