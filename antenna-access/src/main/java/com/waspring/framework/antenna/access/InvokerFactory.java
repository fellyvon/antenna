package com.waspring.framework.antenna.access;

import org.apache.log4j.Logger;

import com.github.rholder.retry.Retryer;
import com.waspring.framework.antenna.access.util.RetryerUtil;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.core.hander.IHander;
import com.waspring.framework.antenna.core.hander.IHanderFactory;
import com.waspring.framework.antenna.core.hander.Invoker;

/**
 * invoker工厂，实际上是委托给了IConfigBeanFactory 来执行。<br/>
 * 同时处理回调，回调注册了具体的重试机制！<br/>
 * 
 * @author felly
 *
 */
public class InvokerFactory implements IHanderFactory {
	private IConfigBeanFactory<Invoker> beanFactory = null;
 
	private Logger log = Logger.getLogger(getClass());
	private IApplication application = null;
 

	public InvokerFactory(IApplication application) throws Exception {
		this.application = application;
		beanFactory = new HanderConfigBeanFactory<Invoker>(application, this);
	 
	}

	public IConfigBeanFactory<Invoker> getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void callback(IHander hander, IConfigure configure) {
		IConfigureInvoker configureInvoker = (IConfigureInvoker) configure;
		String id = configureInvoker.getRetryStrategyID();
		IConfigureRetryStrategy strategy = ((IConfigureContainer)configureInvoker.getParent().getParent())
				.getConfigureRetryStrategys().getConfigureRetryStrategy(id);
		if (strategy != null) {
			Retryer<Boolean> retryer = RetryerUtil.buildRetryer(strategy);
			((AbstractInvokerHander) hander).register(retryer);
		}
		//// POSTURL
		((AbstractInvokerHander) hander).setPostURL(configureInvoker.getPostUrl());

	}

	public void setBeanFactory(IConfigBeanFactory<Invoker> beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * 创建key
	 */
	private HanderKey bornKey(String containerId, String action) {

		return new HanderKey(containerId, action);
	}

	@Override
	public Invoker createHander(String containerId, String action) {
		HanderKey key = bornKey(containerId, action);
		 return  getBeanFactory().create(key);
		 
	}

}
