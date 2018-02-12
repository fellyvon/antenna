package com.waspring.framework.antenna.core;

import java.util.Queue;

import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.core.filter.IFilter;
import com.waspring.framework.antenna.core.hander.IHanderFactory;
import com.waspring.framework.antenna.core.hander.IProvider;
import com.waspring.framework.antenna.core.hander.Invoker;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;

/**
 * 应用定义 
 * 
 * @author felly
 *
 */
public interface IApplication extends ILifecycle {

	<K, V> ICache<K, V> getCache(String cacheName);///// 缓存对象

	String getConfigFilePath();/// 获取配置文件的地址，用于加载配置
	/// 设置配置文件路径。支持协议classpath://,http://,file://

	IApplication setConfigFilePath(String configFilePath);

	//// 申请访问者容器
	IVisitorContainer applyContainer(String containerId);

	/// 获取调用处理程序
	Invoker getInvoker(IVisitorContainer container, String action);

	/// 获取全部访问者容器
	IVisitorContainer[] getVisitorContainers();

	/// 获取当前容器
	IVisitorContainer getCurrentVisitorContainer();

	/**
	 * 获取提供者处理程序
	 * 
	 * @param container
	 * @param action
	 * @return
	 */
	IProvider getProvider(IVisitorContainer container, String action);

	/**
	 * 获取总配置
	 * 
	 * @return
	 */
	IConfigure getRootConfigure();

	/**
	 * 得到invoker工厂
	 * 
	 * @return
	 */
	IHanderFactory getInvokerFactory();

	/**
	 * 得到provider 工厂
	 * 
	 * @return
	 */
	IHanderFactory getProviderFactory();

	/**
	 * 获取过滤器
	 * @param container
	 * @param filterId
	 * @return
	 */
	IFilter getFilter(IVisitorContainer container, String filterId);
	
	/**
	 * 获取队列
	 */
	Queue  getQueue(String queueId);

}
