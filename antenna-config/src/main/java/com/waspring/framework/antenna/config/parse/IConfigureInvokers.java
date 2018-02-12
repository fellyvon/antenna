package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 对应到invoker
 * 
 * @author felly
 *
 */
public interface IConfigureInvokers extends IConfigure {

	void addConfigureInvoker(IConfigureInvoker configureInvoker);

	void removeConfigureInvoker(IConfigureInvoker configureInvoker);

	void setConfigureInvoker(IConfigureInvoker[] configureInvokers);

	IConfigureInvoker[] getConfigureInvokers();

	IConfigureInvoker getIConfigureInvoker(String action);

	String getRetryStrategyID();

	String POST_URL = "post-url";

	String RETRY_STRATEGY_ID = "retry-strategy-id";

	String ZERO = "0";
	
	String   getPostUrl();
}
