package com.qunar.ops.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.qunar.ops.service.AirChinaService;

@Controller
@RequestMapping("airchina")
public class AirChinaLogController {

	private Logger logger = LoggerFactory.getLogger(AirChinaLogController.class);

	@Autowired
	private AirChinaService airchinaService;

	@RequestMapping("log")
	public ModelAndView getLog(@RequestParam(value="orderNo",required=false) String orderNO) throws ParseException {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(orderNO)) {
			logger.info("get airchina log!");
			List<String> logsList = airchinaService.getErrLogOfIssueTicketErrOfAirchina(orderNO);
			if (logsList == null || logsList.size() == 0) {
				viewMap.put("flag", "1");
				viewMap.put("orderNo", orderNO);
			} else {
				viewMap.put("request", DecrateStr(logsList.get(0)));
				viewMap.put("response", DecrateStr(logsList.get(1)));
				viewMap.put("flag", "2");
			}
		} else {
			viewMap.put("flag", "3");
		}
		return new ModelAndView("airchinalog", viewMap);
	}

	private String DecrateStr(String str) {
		StringBuffer sb = new StringBuffer("");
		int len = str.length() / 154;
		for (int i = 0; i <= len; i++) {
			String subString = null;
			if (i != len) {
				subString = str.substring(i * 154, (i + 1) * 154);
			} else {
				subString = str.substring(i * 154);
			}
			sb.append("<xmp>").append(subString).append("</xmp>");
		}
		return sb.toString();
	}

}
