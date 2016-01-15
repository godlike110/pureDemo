package com.waimai.ops.controller;

import java.awt.print.Printable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.handler.codec.http.HttpHeaders.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.NamedNodeMap;

import com.alibaba.fastjson.JSONObject;
import com.sankuai.meituan.waimai.thrift.activity.iface.WmActivityOrderThriftIface.newUserCancelOrderRecall_args;
import com.waimai.ops.annotation.AccessLoginRequired;
import com.waimai.ops.model.Admin;
import com.waimai.ops.model.User;
import com.waimai.ops.service.TestService;

@AccessLoginRequired
@Controller
@RequestMapping("/api/")
public class IndexController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private static final String[] names = {"文志伟","黎亚洲","常泽川","李承宇"};
	
	private static final long startTime = 1452441600;
	
	private static final long aWeekTime = 604800000;
	
	private static Map<Integer,String> nameMap = new HashMap<>();
	
	static {
		
		nameMap.put(0, names[0]);
		nameMap.put(1, names[1]);
		nameMap.put(2, names[2]);
		nameMap.put(3, names[3]);
	}

	@Autowired
	private TestService testService;

	@RequestMapping("newindex")
	public String index(Model model) {

		logger.info("requestId:{}", this.getAccessData().getCurrentRequestId());
		return "index";
	}
	
	@RequestMapping("newindextest")
	@ResponseBody
	public String indextest(Model model) {
        Map<String,String> serverMap = getServerName(System.currentTimeMillis());
        model.addAttribute("thisName", serverMap.get("thisName"));
        model.addAttribute("nextName", serverMap.get("nextName"));
		logger.info("requestId:{}", this.getAccessData().getCurrentRequestId());
		return "index";
	}
	
	@RequestMapping("index")
	public String newIndex(Model model) {
        Map<String,String> serverMap = getServerName(System.currentTimeMillis());
        model.addAttribute("thisName", serverMap.get("thisName"));
        model.addAttribute("nextName", serverMap.get("nextName"));
		logger.info("requestId:{}", this.getAccessData().getCurrentRequestId());
		return "newindex";
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

	private Map<String, String> getServerName(long nowTime) {
		int weeks = (int) ((nowTime - startTime*1000) / aWeekTime);
		int thisWeek = weeks/4;
		int nextWeek = 0;
		if(thisWeek==3) {
			nextWeek = 0;
		} else {
			nextWeek = thisWeek + 1;
		}
		Map<String, String> serverMap = new HashMap<>();
		serverMap.put("thisName", nameMap.get(thisWeek));
		serverMap.put("nextName", nameMap.get(nextWeek));
		return serverMap;
	}
	
}
