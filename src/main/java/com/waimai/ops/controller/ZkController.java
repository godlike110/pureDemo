package com.waimai.ops.controller;

import java.util.List;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.JButton;

import org.apache.camel.processor.interceptor.StreamCachingInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONScanner;
import com.gentlyweb.utils.StringRangeComparator;
import com.waimai.ops.model.TreeData;
import com.waimai.ops.service.ZkService;

@Controller
@RequestMapping("zk")
public class ZkController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkController.class);
	
	@Autowired
	private ZkService zkService;
	
	public static final String SPLIT_STR = "@@";
	
	@RequestMapping("index")
	public String getValue(@RequestParam(value="server",required=false) String server,@RequestParam(value="key",required=false) String key,@RequestParam(value="env",required=false) String env,Model model) throws KeeperException, InterruptedException {
		if(StringUtils.isBlank(key) && StringUtils.isBlank(server) && StringUtils.isBlank(env)) {
			return "zkindex";
		} 
		return "zkindex";
	}
	
	@RequestMapping("getData") 
	@ResponseBody
	public String getDataApi(@RequestParam(value="qs",required=false) String qs) throws KeeperException, InterruptedException {
		if(StringUtils.isBlank(qs)) {
			return getRootData();
		}
		return getOrSetData(qs);
	}
	
	
	@RequestMapping("setData") 
	@ResponseBody
	public String setDataApi(@RequestParam(value="value",required=false) String value, @RequestParam(value="qs",required=false) String qs) throws KeeperException, InterruptedException {
		if(StringUtils.isBlank(qs)) {
			return "failed";
		}
		return getOrSetData(qs);
	}
	
	public String getRootData() {
		JSONObject data = new JSONObject();
		JSONArray jArray = new JSONArray();
		TreeData tData = new TreeData();
		tData.setId("dev");
		tData.setValue("DEV 环境ZK配置");
		tData.setHasChildren(true);
		TreeData tData1 = new TreeData();
		tData1.setId("qa");
		tData1.setValue("QA 环境ZK配置");
		tData1.setHasChildren(true);
		jArray.add(tData);
		jArray.add(tData1);
		data.put("data", jArray);
		return data.toString();
	}
	
	public String getOrSetData(String qs) throws KeeperException, InterruptedException {
		String[] params = qs.split(SPLIT_STR);
		
		switch(params.length) {
		case 1:
			return getServerList(params[0]);
		case 2:
			return getServerConfigList(params[0], params[1]);
		case 3:
			return getConfig(params[0], params[1], params[2]);
		case 4:
			return setConfig(params[0], params[1], params[2],params[3]);
		default:
			return null;
		}
	}
	
	/**
	 * 获取服务列表
	 * @param env
	 * @return
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public String getServerList(String env) throws KeeperException, InterruptedException {
		List<String> serverList = zkService.getConfigServerList(env);
		JSONObject data = new JSONObject();
		JSONArray jArray = new JSONArray();
		for(String server:serverList) {
			TreeData tData = new TreeData();
			tData.setId(env + SPLIT_STR + server );
			tData.setValue(server);
			tData.setHasChildren(true);
			jArray.add(tData);
		}
		data.put("data", jArray);
		return data.toString();
		
	}
	
    /**
     * 获取配置列表
     * @param env
     * @param server
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
	public String getServerConfigList(String env,String server) throws KeeperException, InterruptedException {
		List<String> configs = zkService.getConfigList(server, env);
		JSONObject data = new JSONObject();
		JSONArray jArray = new JSONArray();
		for(String config:configs) {
			TreeData tData = new TreeData();
			tData.setId(env + SPLIT_STR + server + SPLIT_STR + config );
			tData.setValue(config);
			tData.setHasChildren(false);
			jArray.add(tData);
		}
		data.put("data", jArray);
		return data.toString();
		
	}
	
	/**
	 * zk配置
	 * @param env
	 * @param server
	 * @param key
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String getConfig(String env,String server,String key) throws KeeperException, InterruptedException {
		String value = zkService.getConfig(server, key, env);
		JSONObject jObject = new JSONObject();
		jObject.put("key", key);
		jObject.put("value", value);
		jObject.put("qs", env+SPLIT_STR+server);
		return jObject.toString();
	}
	
	/**
	 * zk配置设置
	 * @param env
	 * @param server
	 * @param key
	 * @param value
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String setConfig(String env,String server,String key,String value) throws KeeperException, InterruptedException {
		boolean result = zkService.setConfig(server, key, value, env);
		if(result) {
			return "true";
		} else {
			return "failed";
		}
	}

}
