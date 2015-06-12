package com.qunar.ops.aptconfig;

import org.springframework.stereotype.Component;

import com.qunar.ops.annotation.Config;


/**
 * 运用程序常量配置
 * @author zhiwei.wen
 * @time 2015年4月28日下午2:52:55
 */
@Component
public class AptConstants {

	@Config("test.url")
	public static String TEST_URL ;
	
	@Config("ip.list")
	public static String IP_LIST;
	
	public static String SHELL_PATH="/home/zhiweiwen/myscript/log/";
	
	public static String INFO_MSG = "msg";
	
}
