package net.okdi.core.util.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author chuanshi.chai
 * 
 */
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	// 声明一个静态变量保存
	@Override
	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		// if(contex==null)System.out.println("*********注入失败");
		// else System.out.println("*************注入成功");
		this.context = contex;
	}
	public static ApplicationContext getContext() {
		return context;
	}
	public final static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	public final static Object getBean(String beanName, Class<?> requiredType) {
		return context.getBean(beanName, requiredType);
	}
}
