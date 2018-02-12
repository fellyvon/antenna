package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 
 * @author felly
 *
 */
public interface IConfigureExceptions extends IConfigure {

	IConfigureException[] getConfigureException();

	void addConfigureException(IConfigureException exceptionConfig);

	void removeConfigureException(IConfigureException exceptionConfig);

	IConfigureException getConfigureException(String id);

}
