package com.waimai.ops.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.waimai.ops.utils.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 日志拦截
 * 
 * @author wenzhiwei 2015年12月24日 LoggerInterceptor by eclipse
 */
public class LoggerInterceptor implements ThrowsAdvice, MethodBeforeAdvice, AfterReturningAdvice {
	/** 字符串最大长度 */
	public static final int MAX_STR_LENGTH = 5000;
	private static String hostName = "";

	private static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

	/**
	 * 被拦截方法正常执行以后,此方法被调用
	 *
	 * @param returnValue
	 *            Object 目标方法返回的结果
	 * @param method
	 *            Method 目标方法对象
	 * @param args
	 *            Object[] 目标方法的参数
	 * @param target
	 *            Object 目标类对象
	 *
	 * @throws Throwable
	 */
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

		String methodName = method.getName();
		logger.info("request return | host:{},class:{}，method:{},result:{}", getHostName(),
				target.getClass().getSimpleName(), methodName,
				((returnValue instanceof Object[]) ? CommonUtil.deepToString((Object[]) returnValue, MAX_STR_LENGTH)
						: CommonUtil.deepToString(new Object[] { returnValue }, MAX_STR_LENGTH)));
	}

	/**
	 * 被拦截方法执行前,此方法被调用
	 *
	 * @param method
	 *            Method 目标方法对象
	 * @param args
	 *            Object[] 目标方法的参数
	 * @param target
	 *            Object 目标类对象
	 *
	 * @throws Throwable
	 */
	public void before(Method method, Object[] args, Object target) throws Throwable {

		String methodName = method.getName();
		logger.info("request in | host:{},class:{},method:{},params:{}", getHostName(),
				target.getClass().getSimpleName(), methodName, CommonUtil.deepToString(args, MAX_STR_LENGTH));
	}

	/**
	 * 方法抛出异常以后所执行的方法
	 *
	 * @param method
	 *            Method 目标方法对象
	 * @param args
	 *            Object[] 目标方法的参数
	 * @param target
	 *            Object 目标类对象
	 * @param ex
	 *            Throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Throwable ex) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		int now = (int) (System.currentTimeMillis() / 1000);
		now = now - now % 60;// 精确到分钟
		logger.info("request error |{}| {}| {}| {}| {} |{}", now, request.getRequestURI(),
				target.getClass().getSimpleName(), method.getName(),
				filterParams(CommonUtil.deepToString(args, MAX_STR_LENGTH) + ",CommonData:"
						+ CommonUtil.getThreadLocalValue()),
				getExceptionStr(ex));
	}

	private static String getHostName() {
		try {
			if ("".equals(hostName)) {
				hostName = InetAddress.getLocalHost().getHostName();
			}
		} catch (UnknownHostException e) {

		}
		return hostName;
	}

	/**
	 * 将异常栈的信息和cause拼接为String
	 */
	private String getExceptionStr(Throwable e) {

		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stackTrace = e.getStackTrace();
		if (stackTrace != null && stackTrace.length > 0) {
			sb.append("Cause by:" + e.getCause() + "%26lt%3bbr%2f%26gt%3b");
			for (int i = 0; i < stackTrace.length; i++) {
				sb.append(stackTrace[i].toString() + "%26lt%3bbr%2f%26gt%3b");
			}
		}
		return sb.toString();

	}

	/**
	 * 对字符=进行编码
	 */
	private String filterParams(String params) {
		if (params == null) {
			return null;
		}
		return params.replaceAll("=", "%3d");
	}

}
