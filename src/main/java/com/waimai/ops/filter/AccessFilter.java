package com.waimai.ops.filter;

import com.waimai.ops.model.AccessData;
import com.waimai.ops.utils.CommonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 访问过滤
 * 
 * @author wenzhiwei 2015年12月25日 WmAppAccessFilter by eclipse
 */
public class AccessFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	private String[] ipWhiteLists = { "10.0.0.1-10.255.255.254", "192.168.0.1-192.168.255.254",
			"172.30.0.1-172.30.255.254", "124.251.11.37-124.251.11.37" };

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String ipWhiteList = filterConfig.getInitParameter("ipWhiteList");
		if (ipWhiteList != null) {
			ipWhiteLists = ipWhiteList.split(";");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {

			AccessData accessData = new AccessData();

			CommonUtil.setThreadLocalValue(accessData);
			chain.doFilter(request, response);

		} catch (Exception ex) {
			logger.error("AccessFilter error!",ex);
			Throwable casue = ex.getCause();
			if (casue == null) {
				casue = ex;
			}
			print2Client(httpResponse, "AccessFilter 系统异常");
		}
	}

	private void print2Client(HttpServletResponse httpResponse, String content) throws IOException {
		httpResponse.setContentType("application/json;charset=UTF-8");
		httpResponse.getWriter().println(content);
		httpResponse.getWriter().close();
	}

	/**
	 * 获取访问机器ip
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Override
	public void destroy() {

	}

}
