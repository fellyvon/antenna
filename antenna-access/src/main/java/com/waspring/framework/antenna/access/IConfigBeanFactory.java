package com.waspring.framework.antenna.access;

/**
 * 定义从配置获取对象的抽象
 * 
 * @author felly
 *
 */
public interface IConfigBeanFactory<T> {

	T create(java.io.Serializable id);

	
}
