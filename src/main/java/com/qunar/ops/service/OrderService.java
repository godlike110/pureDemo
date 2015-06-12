package com.qunar.ops.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qunar.ops.aptconfig.AptConstants;
import com.qunar.ops.utils.HttpUtil;

@Service
public class OrderService {

	public static Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    @Autowired
    private ShellService shellService;
    
    private static String[] titlesStrings = {"订单号","机票价格","联系人","联系人手机号","订单状态","客户ip","订单创建时间","出发城市","出发时间","到达城市","订单来源","乘机人","身份证号码"};
    
    
    /**
     * 活取订单信息
     * @param site
     * @param orderNo
     * @return
     * @throws IOException 
     * @throws HttpException 
     */
    public Map<String, String> getOrderInfos(String site,String orderNo) throws HttpException, IOException {
    	if(StringUtils.isBlank(site) || StringUtils.isBlank(orderNo)) {
    		return null;
    	}
    	List<String> orders = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "getOrderInfo.sh " + orderNo + " " + site);
    	Map<String, String> resultMap = new HashMap<String, String>();
    	if(null==orders || orders.size()==0) {
    		return null;
    	}
    	resultMap.put("data", getDate(orders).toString());
    	resultMap.put("title", getTitle().toString());
    	return resultMap;
    }
    
    /**
     * 获取订单联系人和创建时间
     * @param site
     * @param orderNo
     * @return
     */
    public Map<String, String> getOrderCnameAndCtime(String site,String orderNo) {
    	if(StringUtils.isBlank(site) || StringUtils.isBlank(orderNo)) {
    		return null;
    	}
    	List<String> orders = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "getOrderInfo.sh " + orderNo + " " + site);
    	Map<String, String> resultMap = new HashMap<String, String>();
    	decrateMap(resultMap, orders);
    	return resultMap;
    }
    
    private void decrateMap(Map<String,String> map,List<String> orders) {
    	if(null==orders || orders.size()==0)  {
    		return;
    	}
    	String [] orderInfo = orders.get(0).split(" ");
    	map.put("name", orderInfo[2]);
    	map.put("date", orderInfo[6].split("&&")[0]);
    	map.put("time", orderInfo[6].split("&&")[1]);
    	map.put("ip", orderInfo[5]);
    	map.put("price", orderInfo[1].split("\\.")[0]);
    	map.put("fromDate", orderInfo[8].split("&&")[0]);
    	String type = orderInfo[10];
    	map.put("type", type.equals("1")?"wap":"www");
    } 
    
    /**
     * 获取标题
     * @return
     */
	public StringBuffer getTitle() {
		StringBuffer sb = new StringBuffer("<thread><tr>");
		for (String title : titlesStrings) {
			sb.append("<th>").append(title).append("</th>");
		}
		sb.append("</tr></thread>");
		return sb;
	}
	
	public StringBuffer getDate(List<String> data) throws HttpException, IOException {
		StringBuffer sb = new StringBuffer("<tbody>");
		int len = data.size();
		if(data==null || data.size()==0) {
			return null;
		}
		for(int i=0;i<len;i++) {
			sb.append("<tr>");
			
			for(String str:data.get(i).split(" ")) {
				if(str.contains("&&")) {
					str = str.replace("&&", " ");
				}
				if(str.contains("##")) {
					str = decryptId(str.replace("##", ""),0);
				}
				if(str.contains("@@")) {
					str = decryptId(str.replace("@@", ""),1);
				}
				sb.append("<td>").append(str).append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</tbody>");
		return sb;
	}
	
	/**
	 * 
	 * @param idNo  证件号
	 * @param type　证件类型　０　手机　１　身份证
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public String decryptId(String idNo,int type) throws HttpException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idNo", idNo);
		params.put("idType", String.valueOf(type));
		return HttpUtil.postForObject("http://10.86.212.187:8080/flagshiptools/secret/decryptPassengerIdno", params);
	}
	
}
