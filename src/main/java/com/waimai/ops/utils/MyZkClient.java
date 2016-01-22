package com.waimai.ops.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyZkClient {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MyZkClient.class);
	
	protected ZooKeeper zooKeeper;
	
	private String connectString;
	
	private int timeOut;
	
	private Watcher watcher;
	
	public MyZkClient(String connectionString,int timeOut) {
		this.connectString = connectionString;
		this.timeOut = timeOut;
		this.watcher = new ZkWatcher();	
		try {
			zooKeeper = new ZooKeeper(this.connectString, this.timeOut, this.watcher);
		} catch (IOException e) {
			
			LOGGER.error("create zkClient error!",e);
		}
	}
	
	/**
	 * 检查path是否存在
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean checkExist(String path) throws KeeperException, InterruptedException {
		
		
		 if(zooKeeper.exists(path, true)==null) {
			 return false;
		 } 
		 return true;
	}
	
	/**
	 * 获取子目录列表
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public List<String> getChild(String path) throws KeeperException, InterruptedException {
		
		if(checkExist(path)) {
			return zooKeeper.getChildren(path, true);
		}
		return Collections.emptyList();
	}
	
	/**
	 * 创建节点
	 * @param path
	 * @param value
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean create(String path,String value) throws KeeperException, InterruptedException {
		
		if(!checkExist(path)) {
			zooKeeper.create(path, value.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			return true;
		}
		return false;
		
	}
	
	/**
	 * 设值
	 * @param path
	 * @param value
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean set(String path,String value) throws KeeperException, InterruptedException {
		if(checkExist(path)) {
			zooKeeper.setData(path, value.getBytes(), -1);
			return true;
		} else {
			return create(path, value);
		}
	}
	
	/**
	 * 获取zk值
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public String get(String path) throws KeeperException, InterruptedException {
		return new String(zooKeeper.getData(path, false, null));
	}
	
	/**
	 * 关闭客户端
	 * @throws InterruptedException
	 */
	public void close() throws InterruptedException {
		zooKeeper.close();
	}
	
	
	private class ZkWatcher implements Watcher{

		@Override
		public void process(WatchedEvent event) {
			
			LOGGER.info("event - eventPath:{},eventName:{}！",event.getPath(),event.getType().name());
		}
		
	}
	
	

}
