package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 过滤器组
 * @author felly
 *
 */
public interface IConfigureFilters extends IConfigure {

	IConfigureFilter[] getConfigureFilter();

	void addConfigureFilter(IConfigureFilter configureFilter);

	void removeConfigureFilter(IConfigureFilter configureFilter);

	IConfigureFilter getConfigureFilter(String id);

}
