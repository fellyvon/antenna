package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureAttemptTimeLimiter;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public class ConfigureAttemptTimeLimiterImpl extends AbstractConfiure implements IConfigureAttemptTimeLimiter {
	private IConfigureRetryStrategy strategy = null;

	public ConfigureAttemptTimeLimiterImpl(IConfigureRetryStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public long getLimiter() {

		return Long.parseLong(getProperty(IConfigureAttemptTimeLimiter.LIMITER));
	}

	@Override
	public IConfigure getParent() {

		return strategy;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.AttemptTimeLimiter.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

}
