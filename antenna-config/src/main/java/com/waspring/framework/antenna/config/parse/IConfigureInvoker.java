package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 对应到invoker
 * 
 * @author felly
 *
 */
public interface IConfigureInvoker extends IConfigure {
	String SERVICE_CLASS = IConfigureProvider.SERVICE_CLASS;
	String INVOKER_CLASS = "invokerclass";
	String LISTENER="listener";
	String getInvokerClass();

	String getServiceClass();

	String getRetryStrategyID();

	String getPostUrl();
	
	String getListener();

	String POST_URL = "post-url";

}
