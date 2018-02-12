package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public interface IConfigureRetryStrategys extends IConfigure {
	 
	IConfigureRetryStrategy[] getConfigureRetryStrategys();
	
	void addConfigureRetryStrategy(IConfigureRetryStrategy strategy);
	
	void removeConfigureRetryStrategy(IConfigureRetryStrategy strategy);
	
	IConfigureRetryStrategy getConfigureRetryStrategy(String id);

}
