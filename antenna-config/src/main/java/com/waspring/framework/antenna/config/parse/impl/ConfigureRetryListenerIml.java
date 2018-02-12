package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureRetryException;
import com.waspring.framework.antenna.config.parse.IConfigureRetryListener;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.core.config.IConfigure;
/**
 * 
 * @author felly
 *
 */
public class ConfigureRetryListenerIml extends AbstractConfiure
implements IConfigureRetryListener {

	private IConfigureRetryStrategy strategy = null;

	public ConfigureRetryListenerIml(IConfigureRetryStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public String getListener() {
	
		return getProperty(IConfigureRetryListener.LISTENER);
	}

	@Override
	public IConfigure getParent() {
		return strategy;
	}

	@Override
	public String getElementName() {
	
		return ConfigureEnum.RetryListener.getName();
	}

	@Override
	public boolean hasNext() {
	
		return false;
	}

}
