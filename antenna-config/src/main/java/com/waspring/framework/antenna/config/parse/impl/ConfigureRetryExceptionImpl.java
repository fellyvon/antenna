package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureRetryException;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public class ConfigureRetryExceptionImpl extends AbstractConfiure implements IConfigureRetryException {

	private IConfigureRetryStrategy strategy = null;

	public ConfigureRetryExceptionImpl(IConfigureRetryStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public String getException() {

		return getProperty(IConfigureRetryException.EXCEPTION);
	}

	@Override
	public IConfigure getParent() {

		return strategy;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.RetryException.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

}
