package com.waimai.ops.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONObject;
import com.waimai.ops.model.Test1;
import com.waimai.ops.model.Test2;
import com.waimai.ops.model.ThirdType;

public class HttpUtil {

	/**
	 * post
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String postForObject(String url, Map<String, String> params) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator<String> itr = params.keySet().iterator();
		while (itr.hasNext()) {
			String Key = itr.next();
			String value = params.get(Key);
			param.add(new NameValuePair(Key, value));
		}
		post.setRequestBody(param.toArray(new NameValuePair[param.size()]));
		client.executeMethod(post);
		if (post.getStatusCode() == HttpStatus.SC_OK) {
			String response = new String(post.getResponseBodyAsString().getBytes("utf-8"));
			JSONObject json = JSONObject.parseObject(response);
			return json.getString("result");
		}
		return null;
	}

	/**
	 * get
	 * 
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getForObject(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(url);
		client.executeMethod(get);
		if (get.getStatusCode() == HttpStatus.SC_OK) {
			String response = new String(get.getResponseBodyAsString().getBytes("utf-8"));
			return response;
		}
		return null;
	}

	public static void main(String args[]) throws IllegalAccessException, InvocationTargetException {
		Test1 test1 = new Test1();
		test1.setA(1);
		test1.setC(2);
		test1.setThirdType(ThirdType.RENREN);
		Test2 test2 = new Test2();
		BeanUtils.copyProperties(test2, test1);
		System.out.println(JSONObject.toJSONString(test1));
		System.out.println(JSONObject.toJSONString(test2));
	}

}
