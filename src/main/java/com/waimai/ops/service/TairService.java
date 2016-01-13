package com.waimai.ops.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.waimai.ops.utils.MyTairClient;
import com.waimai.ops.utils.TairUtil;

@Service
public class TairService {

	public static Logger logger = LoggerFactory.getLogger(TairService.class);

	private static MyTairClient tair;

	public String getValue(String env, String key) {
		try {
			tair = TairUtil.getTairClient(env);
			if (null == tair) {
				return "";
			}
			return tair.findByKey(key);
		} catch (Exception e) {
			logger.error("error.", e);
			return "";
		}
	}

	public boolean setValue(String env, String key, String value) {
		try {
			tair = TairUtil.getTairClient(env);
			if (null == tair) {
				return false;
			}
			tair.updateKV(key, value);
			return true;
		} catch (Exception e) {
			logger.error("setValue.", e);
			return false;
		}
	}

}
