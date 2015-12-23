package com.waimai.ops.init;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.waimai.ops.annotation.Config;

/**
 * 属性值的一个注入
 * @author zhiwei.wen
 * @time 2015年4月28日下午12:24:29
 */
@Component
public class ConfigAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

	private static Logger logger = LoggerFactory.getLogger(ConfigAnnotationBeanPostProcessor.class);
	
	//创建简单类型转换器
	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();
	
	@Autowired
	private ConfigBean configBean;
	
	@Override
	public boolean postProcessAfterInstantiation(final Object bean,String beanName) throws BeansException {
		System.out.println(beanName);
		if("aptConstants".equals(beanName)) {
			findPropertyAutowiringMetadata(bean);
		}
		return true;
	}
	
    private void findPropertyAutowiringMetadata(final Object bean) {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            public void doWith(Field field) throws IllegalAccessException {
                Config annotation = field.getAnnotation(Config.class);
                if (annotation != null) {
                    Object strValue = configBean.getProperty(annotation.value());
                    if (null != strValue) {
                        Object value = typeConverter.convertIfNecessary(strValue, field.getType());
                        ReflectionUtils.makeAccessible(field);
                        field.set(null, value);
                        logger.info("set field:" + field.getName() + " value :" + value);
                    }
                }
            }
        });
    }
	
}
