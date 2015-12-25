package com.waimai.ops.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waimai.ops.aptconfig.AptConstants;

@Service
public class AirChinaService {

	public static Logger logger = LoggerFactory.getLogger(AirChinaService.class);

	@Autowired
	private ShellService shellService;

	/**
	 * 导出国航错误日志 less
	 * /DATA_LOG_FLIGHT/CN1_nflagshipprovider/l-nflagshipprovider1.f.cn1/201505/
	 * airchina_provider.log.2015-05-22-18.gz
	 * 
	 * @param orderNo
	 * @return
	 * @throws ParseException
	 */
	public List<String> getErrLogOfIssueTicketErrOfAirchina(String orderNo) throws ParseException {
		List<String> timeLogs = shellService
				.execuShell("sh " + AptConstants.SHELL_PATH + "getAirChinaOpTime.sh " + orderNo);
		if (timeLogs == null || timeLogs.size() == 0 || StringUtils.isBlank(timeLogs.get(0))) {
			return null;
		}
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// Date timeDate = sdf.parse(ssh l-flightlogs1.f.cn1 'less
		// /DATA_LOG_FLIGHT/CN1_nflagshipprovider/l-nflagshipprovider*.f.cn1/'$3'/airchina_provider.log.'$2'.gz
		// | grep '$1'');
		String timeString = timeLogs.get(0);
		String dateTime = timeString.split(" ")[0];
		String time = timeString.split(" ")[1];
		String[] logDate = dateTime.split("-");
		String[] logTime = time.split(":");
		List<String> errLogsList = shellService.execuShell("sh " + AptConstants.SHELL_PATH + "searchLog.sh " + orderNo
				+ " " + dateTime + "-" + logTime[0] + " " + logDate[0] + logDate[1]);
		return getError(errLogsList);
	}

	private List<String> getError(List<String> list) {
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if ((i + 1) < list.size()) {
				if (list.get(i).contains("searchOrder request") && list.get(i + 1).contains("searchOrder response")) {
					resultList.add(list.get(i));
					resultList.add(list.get(i + 1));
					break;
				}
			}
		}
		return resultList;
	}

}
