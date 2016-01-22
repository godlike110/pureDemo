package com.waimai.ops.utils;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waimai.ops.enums.Env;

public class ZkUtil {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ZkUtil.class);
	
	private static MyZkClient devZkClient;
	
	private static MyZkClient qaZkClient;
	
	static {
		devZkClient = new MyZkClient("10.4.245.47:9331,10.4.245.49:9331,10.4.245.43:9331", 60000);
		qaZkClient = new MyZkClient("10.4.238.20:9331,10.4.233.31:9331,10.4.232.164:9331", 60000);
	}
	
	public static MyZkClient getZkClient(String env) {
    	Env myEnv = Env.getEnvByDesc(env);
    	switch (myEnv) {
		case QA:
			return qaZkClient;
		case DEV:
			return devZkClient;
		default:
			return null;
		}
	}
	
}

