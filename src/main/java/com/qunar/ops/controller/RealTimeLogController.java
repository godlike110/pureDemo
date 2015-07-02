package com.qunar.ops.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.qunar.ops.aptconfig.AptConstants;
import com.qunar.ops.service.AppLogService;

@Controller
@RequestMapping("realtime")
public class RealTimeLogController {

	public static Logger logger = LoggerFactory.getLogger(RealTimeLogController.class);
	
	@Autowired
	private AppLogService appLogService;
	
	
	@RequestMapping("log")
	public ModelAndView getRealTimeLog(@RequestParam(value="site",required=false) String site,@RequestParam(value="len",required=false) String len) throws ParseException {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(site)) {
			 return new ModelAndView("realtimelogindex", viewMap);
		  }
		  if(StringUtils.isBlank(len)) {
			  len = "0";
		  }
		  if(Integer.parseInt(len)>5) {
			  viewMap.put(AptConstants.INFO_MSG, "服务器可能无法承受如此大步长的日志搜索！");
			  return new ModelAndView("info", viewMap);
		  }
		  String logsList = appLogService.getOnlineError(site, len);
		  viewMap.put("logs", logsList);
 	      return new ModelAndView("realtimelog", viewMap);
	}
}