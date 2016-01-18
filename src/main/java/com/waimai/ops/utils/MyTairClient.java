package com.waimai.ops.utils;

import java.nio.charset.StandardCharsets;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.tair3.client.Result;
import com.taobao.tair3.client.TairClient;
import com.taobao.tair3.client.error.TairException;
import com.taobao.tair3.client.impl.DefaultTairClient;

public class MyTairClient {
	
	public static Logger logger = LoggerFactory.getLogger(MyTairClient.class);
	
    private short C_NS;
    private final static int DEFAULT_TIME_OUT = 100;

    public final static int SERVER_DAY = 7 * 24 * 60 * 60;

    protected DefaultTairClient defaultTairClient;
   
    
    public MyTairClient(short ns,String master,String slaver,String group,String localAppKey,String remoteAppKey) {
    	this.C_NS = ns;
    	defaultTairClient = new DefaultTairClient();
        defaultTairClient.setMaster(master);
        defaultTairClient.setSlave(slaver);
        defaultTairClient.setGroup(group);
        /**设置waimai性能监控appkey 该配置需要去zk中配置为全局配置*/

        defaultTairClient.setLocalAppKey(localAppKey);
        defaultTairClient.setRemoteAppKey(remoteAppKey);

        try {
            defaultTairClient.init();
        } catch (TairException e) {
            logger.error("init tairtestclient error.",e);

            throw new RuntimeException(e);
        }
    }

    public void deleteByKey(String key){
        byte[] key_bytes = key.getBytes(StandardCharsets.UTF_8);
        try {
            Result<Void> result = defaultTairClient.delete(C_NS,key_bytes, new TairClient.TairOption(DEFAULT_TIME_OUT));
        } catch (Exception e) {
            logger.error("deleteByKey error,key:{}",key,e);

            throw new RuntimeException(e);
        }
    }

    public void updateKV(String key,Object value){
        byte[] key_bytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] value_bytes = JSONObject.toJSONString(value).getBytes(StandardCharsets.UTF_8);
        try {
            Result<Void> result = defaultTairClient.put(C_NS, key_bytes, value_bytes,
                    new TairClient.TairOption(DEFAULT_TIME_OUT));
            logger.info("result:{}",JSON.toJSONString(result));
        } catch (Exception e) {
            logger.error("updateKV error,key:{},value:{}",key,value,e);

            throw new RuntimeException(e);
        }
    }

    public void updateKV(String key,Object value,int expireSeconds){
        byte[] key_bytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] value_bytes = JSONObject.toJSONString(value).getBytes(StandardCharsets.UTF_8);
        try {
            Result<Void> result = defaultTairClient.put(C_NS, key_bytes, value_bytes,
                    new TairClient.TairOption(DEFAULT_TIME_OUT, (short) 0, expireSeconds));
        } catch (Exception e) {
            logger.error("updateKV error,key:{},value:{}",key,value,e);

            throw new RuntimeException(e);
        }
    }

    public String findByKey(String key){
        byte[] key_bytes = key.getBytes(StandardCharsets.UTF_8);
        Result<byte[]> result = null;
        try {
            result = defaultTairClient.get(C_NS,
                    key_bytes, new TairClient.TairOption(100));
            if (result.getResult() == null) {
                return null;
            }

            String value = new String(result.getResult(), StandardCharsets.UTF_8);

            return value;
        } catch (Exception e) {
            logger.error("findByKey error,key:{}",key,e);

            throw new RuntimeException(e);
        }
    }


    /**
     * 此函数在除kill-9方式退出外，保证会调用。包括init不成功的时候。因此做清理操作的时候，要先判断对象是否已经被初始化。如
     * if(tair!=null){
     *    tair.close();
     * }
     * */
    public void close() throws Exception{
        defaultTairClient.close();
    }
	

}
