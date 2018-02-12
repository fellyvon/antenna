package com.waspring.framework.antenna.core.service;

/**
 * 服务容器
 * 
 * @author felly
 *
 */
public interface IServiceContainer {

	public IService[] getServices();

	public IService[] getChildServices();

	public IService[] getChildServicesByClass(Class<?> byclass);

	public <T extends IService> T getChildServiceByClass(Class<T> byclass);

}
