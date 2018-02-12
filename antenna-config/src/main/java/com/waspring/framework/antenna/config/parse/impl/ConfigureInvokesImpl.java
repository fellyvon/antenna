package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.config.parse.IConfigureInvokers;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public class ConfigureInvokesImpl extends AbstractConfiure implements IConfigureInvokers {
	@Override
	public String getPostUrl() {
		return getProperty(IConfigureInvokers.POST_URL);
	}

	@Override
	public IConfigureInvoker getIConfigureInvoker(String action) {

		return configureInvokerMap.get(action);
	}

	private Map<String, IConfigureInvoker> configureInvokerMap = new HashMap<String, IConfigureInvoker>();

	private IConfigureContainer container = null;

	public ConfigureInvokesImpl(IConfigureContainer container) {
		this.container = container;
	}

	@Override
	public void addConfigureInvoker(IConfigureInvoker configureInvoker) {
		if (configureInvoker == null) {
			return;
		}
		if (StringUtils.isEmpty(configureInvoker.getId())) {
			throw new RuntimeException(configureInvoker + "不存在id配置！");
		}
		configureInvokerMap.put(configureInvoker.getId(), configureInvoker);
	}

	@Override
	public void removeConfigureInvoker(IConfigureInvoker configureInvoker) {
		if (configureInvoker == null) {
			return;
		}
		configureInvokerMap.remove(configureInvoker.getId());
	}

	@Override
	public void setConfigureInvoker(IConfigureInvoker[] configureInvokers) {
		if (configureInvokers == null || configureInvokers.length == 0) {
			return;
		}

		for (IConfigureInvoker ci : configureInvokers) {
			addConfigureInvoker(ci);
		}
	}

	@Override
	public IConfigureInvoker[] getConfigureInvokers() {
		IConfigureInvoker[] cis = new IConfigureInvoker[configureInvokerMap.size()];
		return configureInvokerMap.values().toArray(cis);
	}

 

	@Override
	public String getRetryStrategyID() {
		return getProperty(IConfigureInvokers.RETRY_STRATEGY_ID);
	}

	@Override
	public IConfigure getParent() {

		return container;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.Invokers.getName();
	}

	@Override
	public boolean hasNext() {

		return getConfigureInvokers().length > 0;
	}

}
