package com.waimai.ops.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.waimai.ops.annotation.AccessLoginRequired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆
 * 
 * @author wenzhiwei 2015年12月24日 LoginCheckInterceptor by eclipse
 */
public class LoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod))
			return true;
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		AccessLoginRequired loginCheck = handlerMethod.getBean().getClass().getAnnotation(AccessLoginRequired.class);
		if (loginCheck != null) {
			logger.info("需要登录");
			return true;
		}

		logger.info("不需要登录");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
