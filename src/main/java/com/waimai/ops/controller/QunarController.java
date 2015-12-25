package com.waimai.ops.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.waimai.ops.aptconfig.AptConstants;
import com.waimai.ops.service.Air9Service;
import com.waimai.ops.service.OrderService;

@RequestMapping("qunar")
@Controller
public class QunarController {

	public static Logger logger = LoggerFactory.getLogger(QunarController.class);

	@Autowired
	private Air9Service air9Service;

	@Autowired
	private OrderService orderService;

	@RequestMapping("index")
	@ResponseBody
	public String index(@RequestParam(value = "pa", required = false) int pa) {
		String url = AptConstants.TEST_URL;
		return url;
	}

	@RequestMapping("page")
	public ModelAndView myPage(@RequestParam(value = "name", required = false) String name) {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		viewMap.put("name", name);
		return new ModelAndView("page", viewMap);
	}

	@RequestMapping("air9")
	public ModelAndView getAir9(@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "flightNo", required = false) String flightNo) throws IOException {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		Map<String, String> htmlMap = air9Service.getAir9Infos(flightNo, date);
		viewMap.put("title", htmlMap.get("title"));
		viewMap.put("data", htmlMap.get("data"));
		return new ModelAndView("air9", viewMap);
	}

	@RequestMapping("air9search")
	public ModelAndView getSearchIndex() throws IOException {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		Map<String, String> htmlMap = air9Service.getAir9Infos("", "");
		viewMap.put("title", htmlMap.get("title"));
		viewMap.put("data", htmlMap.get("data"));
		return new ModelAndView("air9search", viewMap);
	}

	@RequestMapping("ordersearch")
	public ModelAndView getOrderInfo(@RequestParam(value = "site", required = false) String site,
			@RequestParam(value = "orderNo", required = false) String orderNo) throws IOException {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		Map<String, String> htmlMap = orderService.getOrderInfos(site, orderNo);
		if (null != htmlMap) {
			viewMap.put("title", htmlMap.get("title"));
			viewMap.put("data", htmlMap.get("data"));
		}
		return new ModelAndView("orderSearch", viewMap);
	}

}
