package com.waimai.ops.aptconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.jdbc.core.metadata.Db2CallMetaDataProvider;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.waimai.ops.annotation.Config;

/**
 * 运用程序常量配置
 * 
 * @author zhiwei.wen
 * @time 2015年4月28日下午2:52:55
 */
@Component
public class AptConstants {

	@Config("test.url")
	public static String TEST_URL;

	@Config("ip.list")
	public static String IP_LIST;

	public static String SHELL_PATH = "/home/zhiweiwen/workspace/mywork/flightops/flightops/etc/sh/";

	public static String INFO_MSG = "msg";

	public static class Model {
		long id;
		long type;

		public Model(long id, long type) {
			this.id = id;
			this.type = type;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getType() {
			return type;
		}

		public void setType(long type) {
			this.type = type;
		}

	}

	public static void main(String args[]) {
		// Integer a = Integer.MAX_VALUE;
		// String bString = "首单立减15元（在线支付专享）";
		// List<Model> modellist = new ArrayList<Model>();
		// Map<Long, Set<Long>> setMap = new HashMap<Long, Set<Long>>();
		// for(Long i=(long) 0;i<10;i++) {
		// for(Long j=(long) 0;j<=4;j++) {
		// Model c = new Model(i, j);
		// modellist.add(c);
		// }
		// }
		// System.out.println("modellist：{}"+JSONObject.toJSONString(modellist));
		//
		// for(Model model :modellist) {
		// Set<Long> modelSet = new HashSet<Long>();
		// modelSet = setMap.get(model.getId());
		// if(null==modelSet) {
		// Set<Long> mySet = new HashSet<Long>();
		// mySet.add(model.getType());
		// setMap.put(model.getId(), mySet);
		// } else {
		// modelSet.add(model.getType());
		// }
		// }
		// System.out.println("map：{}"+JSONObject.toJSONString(setMap));
		StringBuffer sBuffer = new StringBuffer("");
		String onlyMeituanShow = "";
		String categoryName = "甜点饮品";
		String timeLimit = sBuffer.toString();
		StringBuffer title = new StringBuffer();
		if (org.apache.commons.lang.StringUtils.isNotBlank(onlyMeituanShow)
				|| org.apache.commons.lang.StringUtils.isNotBlank(timeLimit)
				|| org.apache.commons.lang.StringUtils.isNotBlank(categoryName)) {
			title.append("（限")
					.append(StringUtils.isBlank(categoryName) ? ""
							: StringUtils.isBlank(timeLimit) && StringUtils.isBlank(onlyMeituanShow) ? categoryName
									: categoryName + "、")
					.append(StringUtils.isBlank(timeLimit) ? ""
							: StringUtils.isBlank(onlyMeituanShow) ? timeLimit : timeLimit + "、")
					.append(StringUtils.isBlank(onlyMeituanShow) ? "" : onlyMeituanShow).append("）").toString();
		}
		System.out.println(title.toString());

		String bc = "{wm_poi_id}/{spu_id}";
		String db = bc.replace("{wm_poi_id}", "a").replace("{spu_id}", "b");
		System.out.println("db:" + db);
		System.out.println("bc:" + bc);

	}

}
