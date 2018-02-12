package com.waspring.framework.antenna.config;

import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.core.ILifecycle;

/***
 * 配置抽象，定义类配置的来源路径和输出配置类
 * 
 * @author felly
 *
 */
public interface IConfiguration extends ILifecycle {
	String getConfigPath();

	IConfigureApplication getConfigureApplication();

	void setConfigPath(String configPath);
}
