package com.waimai.ops.enums;

import java.util.HashMap;
import java.util.Map;

public enum Env {
	
	QA(32,"qa"),
	DEV(3,"dev");
	
	private int code;
	private String desc;
	
	Env(int code,String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	private static Map<String,Env> envMap =  new HashMap<>();
	
	static {
		for(Env env:Env.values()) {
			envMap.put(env.desc, env);
		}
	}
	
	public static Env getEnvByDesc(String key) {
		return envMap.get(key);
	}

}
