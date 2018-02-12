package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStop;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.core.config.IConfigure;

public class ConfigureRetryStopImpl extends AbstractConfiure implements IConfigureRetryStop {

	private IConfigureRetryStrategy strategy = null;

	public ConfigureRetryStopImpl(IConfigureRetryStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public String getStop() {

		return getProperty(IConfigureRetryStop.STOP);
	}

	@Override
	public long delayinmillis() {

		return Long.parseLong(getProperty(IConfigureRetryStop.DELAYINMILLIS));
	}

	@Override
	public int attemptnumber() {

		return Integer.parseInt(getProperty(IConfigureRetryStop.ATTEMPTNUMBER));
	}

	@Override
	public IConfigure getParent() {

		return strategy;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.StopStrategy.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

}
