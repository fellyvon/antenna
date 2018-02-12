package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 请求Id容器配置定义
 * @author felly
 *
 */
public interface IConfigureIDGenerators extends IConfigure {

	void addConfigureIDGenerators(IConfigureIDGenerator idConfigure);

	IConfigureIDGenerator getConfigureIDGenerator(String id);

	void removeConfigureIDGenerator(IConfigureIDGenerator idConfigure);

	IConfigureIDGenerator[] getIConfigureIDGenerators();
}
