package com.waimai.ops.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.waimai.ops.service.AppLogService;


@Controller
@RequestMapping("log")
public class LogController {

	public static Logger logger = LoggerFactory.getLogger(LogController.class);
	
	@Autowired
	private AppLogService appLogService;
	
	@RequestMapping("bclog")
	public ModelAndView getBookingAndCreatelog(@RequestParam(value="site",required=false) String site,@RequestParam(value="orderNo",required=false) String orderNo) throws ParseException, HttpException, IOException {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(site) || StringUtils.isBlank("orderNo")) {
			viewMap.put("flag", 3);
			return new ModelAndView("bclogindex",viewMap);
		} else {
			Map<String, String> bclog = appLogService.getBookingAndCreateLog(site, orderNo);
			if(bclog!=null) {
			viewMap.put("cl", bclog.get("clog"));
			viewMap.put("bl", bclog.get("blog"));
			viewMap.put("flag", 2);
			} else {
				viewMap.put("flag", 1);
				return new ModelAndView("bclogindex",viewMap);
			}
		}
		return new ModelAndView("bclog", viewMap);
	}
	
	@RequestMapping("phonecode")
	public ModelAndView getPhoneCode(@RequestParam(value="site",required=false) String site,@RequestParam(value="orderNo",required=false) String orderNo) throws ParseException {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(site) || StringUtils.isBlank("orderNo")) {
			viewMap.put("flag", 3);
		} else {
			Map<String, String> result = appLogService.getPhoneCode(site, orderNo);
			if(null!=result ) {
			viewMap.put("code", result.get("code"));
			viewMap.put("time", result.get("time"));
			viewMap.put("no", orderNo);
			viewMap.put("flag", 2);
			} else {
				viewMap.put("flag", 1);
				viewMap.put("no", orderNo);
			}
		}
		return new ModelAndView("phonecode", viewMap);
	}
	
	
}
