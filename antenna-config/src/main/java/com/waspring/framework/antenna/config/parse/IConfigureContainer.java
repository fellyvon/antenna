package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 对应到Container
 * 
 * @author felly
 *
 */
public interface IConfigureContainer extends IConfigure {
	String ALLOW_MAX_NUM = "allowmaxnum";
	String ID_GENERATOR = "id-generator";
	String TRANSFER_TIMEOUT = "transfer-timeout";
	String CONTAINER_CLASS = "containerclass";
	String FILTER="filter";
	String LOG_QUEUE="log-queue";
	String LOG_MODE="log-mode";////日志存储模式
	
	
	int getAllowMaximum();

	IConfigureProviders getConfigureProviders();

	IConfigureInvokers getConfigureInvokers();

	void setConfigureProviders(IConfigureProviders configureProvider);

	void setConfigureInvokers(IConfigureInvokers configureInvoker);

	String getIDGenerator();

	IConfigureRetryStrategys getConfigureRetryStrategys();

	void setConfigureRetryStrategys(IConfigureRetryStrategys configureRetryStrategys);

	String getContainerClass();

	long getTransferTimeout();////////// 允许的传输超时时间

	IConfigureIDGenerators getConfigureIDGenerators();

	void setConfigureIDGenerators(IConfigureIDGenerators configureIDGenerators);

	IConfigureExceptions getConfigureExceptions();

	void setConfigureExceptions(IConfigureExceptions configureExceptions);

	IConfigureFilters getConfigureFilters();

	void setConfigureFilters(IConfigureFilters configureFilters);
	
	
	String getFilter();
	
	String getLogQueue();
	String getLogMode();
	
	
}
