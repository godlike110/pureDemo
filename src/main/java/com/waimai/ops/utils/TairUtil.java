package com.waimai.ops.utils;

import com.taobao.tair3.client.impl.DefaultTairClient;
import com.waimai.ops.enums.Env;

public class TairUtil {
	
	
	private static MyTairClient devTair;
	
	private static MyTairClient qaTair;
	
	
	static {
		devTair = new MyTairClient((short) 3, "10.4.246.122:5198", "10.4.246.123:5198", "group_1", "waimai_c_poi_databus", "com.sankuai.tair.waimai.server");	
		qaTair = new MyTairClient((short) 32, "10.4.246.122:5198", "10.4.246.123:5198", "group_1", "waimai_c_poi_databus", "com.sankuai.tair.waimai.server");	
	}
	
    public static MyTairClient getTairClient(String env) {
    	Env myEnv = Env.getEnvByDesc(env);
    	switch (myEnv) {
		case QA:
			return qaTair;
		case DEV:
			return devTair;
		default:
			return null;
		}
    }

}
