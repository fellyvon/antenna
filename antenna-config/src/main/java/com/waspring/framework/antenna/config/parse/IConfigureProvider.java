package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 对应到provider
 * 
 * @author felly
 *
 */
public interface IConfigureProvider extends IConfigure {
	String PROVIDER_CLASS = "providerclass";
	String SERVICE_CLASS = "serviceclass";
	String LISTENER="listener";

	String getProviderClass();

	String getServiceClass();
	
	String getListener();

}
