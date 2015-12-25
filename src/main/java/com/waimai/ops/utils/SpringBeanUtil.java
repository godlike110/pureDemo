package com.waimai.ops.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by liujinye on 14-5-27.
 */
@Service
public final class SpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext ctx;

	/**
	 * 通过spring配置文件中配置的bean id取得bean对象
	 * 
	 * @param id
	 *            spring bean ID值
	 * @return spring bean对象
	 */
	public static Object getBean(String id) {
		if (ctx == null) {
			throw new NullPointerException("ApplicationContext is null");
		}
		return ctx.getBean(id);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
		ctx = applicationcontext;
	}

}
