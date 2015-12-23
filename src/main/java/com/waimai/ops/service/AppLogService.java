package com.waimai.ops.service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waimai.ops.aptconfig.AptConstants;
import com.waimai.ops.utils.HttpUtil;

@Service
public class AppLogService {

	public static Logger logger = LoggerFactory.getLogger(AppLogService.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShellService shellService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

	/**
	 * 获取订单日志
	 * @param site
	 * @param orderNo
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public Map<String, String> getBookingAndCreateLog(String site, String orderNo) throws ParseException,
			HttpException, IOException {
		Map<String, String> infos = orderService.getOrderCnameAndCtime(site, orderNo);
		if (infos == null) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		String name = infos.get("name");
		String date = infos.get("date");
		String time = infos.get("time");
		String timeh = time.split(":")[0];
		String ip = infos.get("ip");

		String fromDate = infos.get("fromDate");
		String type = infos.get("type");
		List<String> createLogList = new ArrayList<String>();
		if (type.equals("www")) {
			createLogList = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "searchbcLog.sh create " + site
					+ " " + date + " " + timeh + " " + name + " " + fromDate + " " + date.split("-")[0] + " "
					+ date.split("-")[1]);
			String deadLine = date + " " + time + ",999";
			String clog = getRightLog(createLogList, deadLine);
			result.put("clog", clog);
			List<String> bookingLog = shellService.execuShell("sh " + AptConstants.SHELL_PATH
					+ "searchbcLog.sh booking " + site + " " + date + " " + timeh + " " + ip + " " + date.split("-")[0]
					+ " " + date.split("-")[1]);
			if (bookingLog == null || bookingLog.size() == 0 || StringUtils.isBlank(bookingLog.get(0))) {
				String timeb = String.valueOf(Integer.parseInt(timeh) - 1);
				bookingLog = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "searchbcLog.sh booking " + site
						+ " " + date + " " + timeb + " " + ip + " " + date.split("-")[0] + " " + date.split("-")[1]);
			}
			if (StringUtils.isNotBlank(bookingLog.get(0))) {
				result.put("blog", decretebLog(getRightLog(bookingLog, clog.split("\\[")[0])));
			}
		} else {
			createLogList = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "searchbcLogwap.sh create "
					+ site + " " + date + " " + timeh + " " + name + " " + fromDate + " " + date.split("-")[0] + " "
					+ date.split("-")[1]);
			String deadLine = date + " " + time + ",999";
			String clog = getRightLog(createLogList, deadLine);
			result.put("clog", clog);
			List<String> bookingLog = shellService.execuShell("sh " + AptConstants.SHELL_PATH
					+ "searchbcLogwap.sh booking " + site + " " + date + " " + timeh + " mobileUid="
					+ clog.split("mobileUid=")[1].split(",")[0] + " " + date.split("-")[0] + " " + date.split("-")[1]);
			if (bookingLog == null || bookingLog.size() == 0 || StringUtils.isBlank(bookingLog.get(0))) {
				String timeb = String.valueOf(Integer.parseInt(timeh) - 1);
				bookingLog = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "searchbcLogwap.sh booking "
						+ site + " " + date + " " + timeb + " mobileUid=" + clog.split("mobileUid=")[1].split(",")[0]
						+ date.split("-")[0] + " " + date.split("-")[1]);
			}
			if (StringUtils.isNotBlank(bookingLog.get(0))) {
				result.put("blog", getRightLog(bookingLog, clog.split("\\[")[0]));
			}
		}
		return result;
	}

	/**
	 * 查询订单手机验证码
	 * @param site
	 * @param orderNo
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, String> getPhoneCode(String site, String orderNo) throws ParseException {
		if (StringUtils.isBlank(site) || StringUtils.isBlank(orderNo)) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		List<String> tgqLogs = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "getphonecodefromtgq.sh "
				+ orderNo + " " + site);
		String plog = getRightLog(tgqLogs, String.valueOf(sdf.format(new Date())));
		if (StringUtils.isNotBlank(plog)) {
			result.put("code", plog.split("验证码：")[1].split("。")[0]);
			result.put("time", plog.split(",")[0]);
		}
		return result;
	}

	/**
	 * 活取线上实时错误
	 * @param site
	 * @return
	 * @throws ParseException 
	 */
	public String getOnlineError(String site, String len) throws ParseException {
		List<String> errs = new ArrayList<String>();
		errs = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "onlineerr.sh " + site + " " + len);
		if (errs.size() == 0 || StringUtils.isBlank(errs.get(0))) {
			return null;
		}
		StringBuffer sb = new StringBuffer("<pre>");
		//errs = getSortedLogByTime(errs);
		String pattern3 = "";
		String time = errs.get(0).split("-")[0];
		try {
			for (String str : errs) {
				if (StringUtils.isBlank(str)) {
					continue;
				}
				if (str.contains(time)) {
					pattern3 = str.split(" ")[3];
					str = str.replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace(" +", " ");
					if (pattern3.equals("ERROR")) {
						str = str.replace(pattern3, "<font color=red size=4>" + pattern3 + "</font>");
					}
				}
				str = "<font color=blue>" + str + "</font><br/>";
				sb.append(str);
			}
			sb.append("</pre>");
		} catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
		return sb.toString();
	}

	public List<String> getSortedLogByTime(List<String> logs) throws ParseException {
		TreeMap<Long, String> treeMap = new TreeMap<Long, String>(new Comparator<Long>() {

			@Override
			public int compare(Long o1, Long o2) {
				// TODO Auto-generated method stub
				BigInteger b1 = new BigInteger(o1.toString());
				BigInteger b2 = new BigInteger(o2.toString());
				if (b1.intValue() > b2.intValue()) {
					return 1;
				} else if (b1.intValue() < b2.intValue()) {
					return -1;
				}
				return 0;
			}
		});
		for (String str : logs) {
			String[] times = str.split(" ");
			treeMap.put(sdf.parse(times[0] + " " + times[1]).getTime(), str);
		}
		Iterator<Long> it = treeMap.keySet().iterator();
		List<String> newlog = new ArrayList<String>();
		while (it.hasNext()) {
			newlog.add(treeMap.get(it.next()));
		}
		return newlog;
	}

	/**
	 * 处理 日志
	 * @param listt
	 * @throws ParseException 
	 */
	private String getRightLog(List<String> list, String deadLine) throws ParseException {
		if (list == null || list.size() == 0) {
			return null;
		}
		Map<Long, String> logMap = new HashMap<Long, String>();
		long ct = sdf.parse(deadLine).getTime();
		long flag = 0;
		String time = "";
		long timeLong = 0;
		for (String log : list) {
			if (StringUtils.isBlank(log)) {
				continue;
			}
			time = log.split("\\[")[0];
			timeLong = sdf.parse(time).getTime();
			logMap.put(timeLong, log);
			if (timeLong > flag && timeLong < ct) {
				flag = timeLong;
			}
		}
		return logMap.get(flag);
	}

	private String decretebLog(String str) throws HttpException, IOException {
		String param = str.split("d=")[2].split(",")[0];
		@SuppressWarnings("deprecation")
		String decrytResponse = HttpUtil
				.getForObject("http://10.86.212.187:8080/flagshiptools/secret/simpleDecrypt?content="
						+ URLEncoder.encode(param) + "&_=" + System.currentTimeMillis());
		JSONObject json = JSONObject.parseObject(decrytResponse);
		return str.replace("d=" + param, "<font color=red>请求参数：【" + json.getString("data") + "】</font>");
	}

	//private String decretecLog(String str) {
//		if(StringUtils.isBlank(str)) {
//			return null;
//		}
	//String tgq = str.split("ctgqCond=")[1].split("goFlights")[0];
	//String tgq = "";
	//String ct = str.split("goFlights\\[0\\].ct")[1].split("passenger\\[")[0];
	//String hh = str.split(" goFlights\\[0\\].pt")[1].split("totalPrice=")[0];
	//String ee= str.split("insInfo\\.price")[1].split(" fromDate=")[0];
	//return str.split("contactEmail=")[0].replace("goFlights[0].de", "<font color=red>出发地</font>").replace("goFlights[0].dd", "<font color=red>出发日期</font>").replace("goFlights[0].dt", "<font color=red>出发时间</font>").replace("totalPrice=","<font color=red>机票总价=</font>").replace("ctgqCond="+tgq,"").replace(ct, "").replace(hh, "").replace(ee, "");
//	}

}
