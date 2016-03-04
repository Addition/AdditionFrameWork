/**
 * additionframe
 * SpringUtil.java
 * 2015年12月3日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * TODO Add class comment here<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author Addition
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2015年12月3日  LiangJiahao    created<br/>
 * <p/> 
 */
@Component
public class SpringUtil implements ApplicationContextAware 
{
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * 
	 * @param name
	 *            Bean注册名称
	 * 
	 * @return bean实例
	 * 
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
}
