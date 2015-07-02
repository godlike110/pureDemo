package com.qunar.ops.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class Air9Service {

	public static Logger logger = LoggerFactory.getLogger(Air9Service.class);

	/**
	 * 9元航班时刻表
	 * @param flightNo
	 * @param date
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getAir9Infos(String flightNo, String date) throws IOException {
		String urlString = "http://crew.9air.com/flight_history_body_out.jsp";
		if(StringUtils.isNotBlank(date)) {
			urlString = urlString + "?submit2=go&flightdate=" + date;
		}
		Document doc = Jsoup.connect(urlString)
				.get();
		return getinfos(doc, date, flightNo);
	}

	private Map<String, String> getinfos(Document doc, String date, String flightNo) {
		Elements tables = doc.getElementsByTag("table");
		ListIterator<Element> iterator = tables.listIterator();
		Map<String, String> resultMap = new HashMap<String, String>();
		int i = 0;
		while (iterator.hasNext()) {
			if (i >= 2) {
				break;
			}
			Element table = iterator.next();
			if (i == 0) {
				String titleString = getTitle(table).toString();
				resultMap.put("title", titleString);
			}
			if (i == 1) {
				String dataString = getData(table, flightNo).toString();
				resultMap.put("data", dataString);
			}
			i++;
		}
		return resultMap;
	}

	public StringBuffer getTitle(Element ele) {
		Elements tds = ele.getElementsByTag("td");
		StringBuffer sb = new StringBuffer("<thread><tr>");
		int i=0;
		for (Element td : tds) {
			if(i<15) {
				sb.append("<th>").append(td.text()).append("</th>");
			}
			i++;
		}
		sb.append("</tr></thread>");
		return sb;
	}

	private StringBuffer getData(Element ele, String flightNo) {
		Elements trs = ele.getElementsByTag("tr");
		StringBuffer sb = new StringBuffer("<tbody>");
		for (Element tr : trs) {
			if (StringUtils.isNotBlank(flightNo)) {
				if (tr.toString().contains(flightNo)) {
					Elements tds = tr.getElementsByTag("td");
					sb.append("<tr>");
					int i = 0;
					for (Element td : tds) {
						if(i<15) {
							sb.append("<td>").append(td.text()).append("</td>");
						}
						i++;
					}
					sb.append("</tr>");
				}
			} else {
				Elements tds = tr.getElementsByTag("td");
				sb.append("<tr>");
				int i=0;
				for (Element td : tds) {
					if(i<15){
						sb.append("<td>").append(td.text()).append("</td>");
					}
					i++;
				}
				sb.append("</tr>");
			}
		}
		sb.append("</tbody>");
		return sb;
	}

}
