package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureProvider;
import com.waspring.framework.antenna.config.parse.IConfigureProviders;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public class ConfigureProvidersImpl extends AbstractConfiure implements IConfigureProviders {
	@Override
	public IConfigureProvider getConfigureProvider(String action) {
		 return   configureProviderMap.get(action);
	}

	private Map<String, IConfigureProvider> configureProviderMap = new HashMap<String, IConfigureProvider>();

	private IConfigureContainer container = null;

	public ConfigureProvidersImpl(IConfigureContainer container) {
		this.container = container;
	}

	@Override
	public void addConfigureProvider(IConfigureProvider configureProvider) {
		if (configureProvider == null) {
			return;
		}
		if (StringUtils.isEmpty(configureProvider.getId())) {
			throw new RuntimeException(configureProvider + "不存在id配置！");
		}
		configureProviderMap.put(configureProvider.getId(), configureProvider);
	}

	@Override
	public void removeConfigureProvider(IConfigureProvider configureProvider) {
		if (configureProvider == null) {
			return;
		}
		configureProviderMap.remove(configureProvider.getId());
	}

	@Override
	public void setConfigureProvider(IConfigureProvider[] configureProviders) {
		if (configureProviders == null || configureProviders.length == 0) {
			return;
		}

		for (IConfigureProvider ci : configureProviders) {
			addConfigureProvider(ci);
		}
	}

	@Override
	public IConfigureProvider[] getConfigureProviders() {
		IConfigureProvider[] cis = new IConfigureProvider[configureProviderMap.size()];
		return configureProviderMap.values().toArray(cis);
	}

	@Override
	public IConfigure getParent() {

		return container;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.Providers.getName();
	}

	@Override
	public boolean hasNext() {
		return getConfigureProviders().length > 0;
	}

}
