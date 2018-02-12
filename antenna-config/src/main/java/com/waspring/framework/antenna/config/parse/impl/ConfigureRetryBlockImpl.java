package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureRetryBlock;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.core.config.IConfigure;

public class ConfigureRetryBlockImpl extends AbstractConfiure implements IConfigureRetryBlock {
	private IConfigureRetryStrategy strategy = null;

	public ConfigureRetryBlockImpl(IConfigureRetryStrategy strategy) {
		this.strategy = strategy;
	}
	@Override
	public long sleepTime() {
		return Long.parseLong(getProperty(SLEEP_TIME));
	}

	@Override
	public IConfigure getParent() {
		 
		return strategy;
	}

	@Override
	public String getElementName() {
	 
		return ConfigureEnum.BlockStrategies.getName();
	}

	@Override
	public boolean hasNext() {
		return false;
	}

}
