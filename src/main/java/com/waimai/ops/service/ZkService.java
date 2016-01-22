package com.waimai.ops.service;

import org.slf4j.LoggerFactory;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.omg.CORBA.NVList;
import org.slf4j.Logger;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Service;

import com.waimai.ops.utils.ZkUtil;

@Service
public class ZkService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ZkService.class);
	
	public static final String rootPath = "/sankuai/webapp";
	
	/**
	 * 获取服务列表
	 * @param env
	 * @return
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public List<String> getConfigServerList(String env) throws KeeperException, InterruptedException {
		return ZkUtil.getZkClient(env).getChild(rootPath);
	}
	
	/**
	 * 获取配置列表
	 * @param server
	 * @param env
	 * @return
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public List<String> getConfigList(String server,String env) throws KeeperException, InterruptedException {
		
		String path = rootPath + "/" + server;
		return ZkUtil.getZkClient(env).getChild(rootPath + "/" + server + "/config");
		
	}

	/**
	 * 获取配置
	 * @param server
	 * @param key
	 * @param env
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String getConfig(String server,String key,String env) throws KeeperException, InterruptedException {
		
		String path = rootPath + "/"+ server + "/" + key;
		LOGGER.info("getconfig,server:{},key:{},env:{}",server,key,env);
		return ZkUtil.getZkClient(env).get(path);
	}
	
	/**
	 * 设置值
	 * @param server
	 * @param key
	 * @param value
	 * @param env
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean setConfig(String server,String key,String value,String env) throws KeeperException, InterruptedException {
		LOGGER.info("getconfig,server:{},key:{},value:{},env:{}",server,key,value,env);
		String path = rootPath + "/"+ server + "/" + key;
		return ZkUtil.getZkClient(env).set(path, value);
	}

}
