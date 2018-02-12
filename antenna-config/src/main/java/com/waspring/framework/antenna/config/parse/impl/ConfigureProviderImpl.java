package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.config.parse.IConfigureProvider;
import com.waspring.framework.antenna.config.parse.IConfigureProviders;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 接口受理配置
 * 
 * @author felly
 *
 */
public class ConfigureProviderImpl extends AbstractConfiure implements IConfigureProvider {

	private IConfigureProviders providers;
	@Override
	public String getListener() {
		return getProperty(IConfigureInvoker.LISTENER);
	}
	@Override
	public String getProviderClass() {

		return getProperty(IConfigureProvider.PROVIDER_CLASS);
	}

	@Override
	public String getServiceClass() {
		return getProperty(IConfigureProvider.SERVICE_CLASS);
	}

	public ConfigureProviderImpl(IConfigureProviders providers) {
		this.providers = providers;
	}

	@Override
	public IConfigure getParent() {

		return providers;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.Provider.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

}
