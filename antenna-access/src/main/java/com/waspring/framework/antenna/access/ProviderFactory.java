package com.waspring.framework.antenna.access;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.hander.IHanderFactory;
import com.waspring.framework.antenna.core.hander.IProvider;

/**
 * hander工厂，实际上是委托给了IConfigBeanFactory 来执行
 * 
 * @author felly
 *
 */
public class ProviderFactory implements IHanderFactory {
 
	private IConfigBeanFactory<IProvider> beanFactory = null;

	private Logger log = Logger.getLogger(getClass());

	private IApplication application = null;
 

	public ProviderFactory(IApplication application) throws Exception {
		this.application = application;
		beanFactory = new HanderConfigBeanFactory<IProvider>(application, this);
		 
	}

	public IConfigBeanFactory<IProvider> getBeanFactory() {

		return beanFactory;
	}

	@Override
	public void callback(IHander hander, IConfigure configure) {
		/// 没什么需要处理的

	}

	public void setBeanFactory(IConfigBeanFactory<IProvider> beanFactory) {
		this.beanFactory = beanFactory;

	}

	/**
	 * 创建key
	 */
	private HanderKey bornKey(String containerId, String action) {

		return new HanderKey(containerId, action);
	}

	@Override
	public IProvider createHander(String containerId, String action) {
		HanderKey key = bornKey(containerId, action);
		return  getBeanFactory().create(key);
		 

	}

}
