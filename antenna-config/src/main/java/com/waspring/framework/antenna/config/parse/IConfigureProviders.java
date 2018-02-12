package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 对应到provider
 * 
 * @author felly
 *
 */
public interface IConfigureProviders extends IConfigure {
	
	void addConfigureProvider(IConfigureProvider configureProvider);

	void removeConfigureProvider(IConfigureProvider configureProvider);

	void setConfigureProvider(IConfigureProvider[] configureProviders);

	IConfigureProvider[] getConfigureProviders();

	IConfigureProvider getConfigureProvider(String action);
	
	
}
