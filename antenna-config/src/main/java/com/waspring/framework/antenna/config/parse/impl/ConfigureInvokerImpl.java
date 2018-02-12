package com.waspring.framework.antenna.config.parse.impl;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.config.parse.IConfigureInvokers;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 接口调用配置
 * 
 * @author felly
 *
 */
public class ConfigureInvokerImpl extends AbstractConfiure implements IConfigureInvoker {

	@Override
	public String getListener() {
		return getProperty(IConfigureInvoker.LISTENER);
	}

	@Override
	public String getPostUrl() {
		String url= getProperty(IConfigureInvoker.POST_URL);
		if(StringUtils.isEmpty(url)) {
			url= getParent().getProperty(IConfigureInvoker.POST_URL);
		}
		
		return url;
	}

	private IConfigureInvokers invokes;

	public ConfigureInvokerImpl(IConfigureInvokers invokes) {
		this.invokes = invokes;
	}

	@Override
	public IConfigure getParent() {
		return invokes;
	}

	@Override
	public String getElementName() {
		return ConfigureEnum.Invoker.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

	@Override
	public String getInvokerClass() {
		return getProperty(IConfigureInvoker.INVOKER_CLASS);
	}

	@Override
	public String getServiceClass() {
		return getProperty(IConfigureInvoker.SERVICE_CLASS);
	}

	@Override
	public String getRetryStrategyID() {
		String strategy = "";
		strategy = getProperty(IConfigureInvokers.RETRY_STRATEGY_ID);
		if (StringUtils.isEmpty(strategy)) {
			strategy = getParent().getProperty(IConfigureInvokers.RETRY_STRATEGY_ID);
		}
		return strategy;
	}

}
