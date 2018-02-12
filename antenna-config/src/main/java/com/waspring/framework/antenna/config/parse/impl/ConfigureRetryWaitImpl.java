package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.config.parse.IConfigureRetryWait;
import com.waspring.framework.antenna.core.config.IConfigure;

public class ConfigureRetryWaitImpl extends AbstractConfiure implements IConfigureRetryWait {

	private IConfigureRetryStrategy strategy = null;

	public ConfigureRetryWaitImpl(IConfigureRetryStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public String getWait() {

		return getProperty(IConfigureRetryWait.WAIT);
	}

	@Override
	public long sleeptime() {

		return Long.parseLong(getProperty(IConfigureRetryWait.SLEEP_TIME));
	}

	@Override
	public long increment() {

		return Long.parseLong(getProperty(IConfigureRetryWait.INCREMENT));

	}

	@Override
	public IConfigure getParent() {

		return strategy;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.WaitStrategy.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

}
