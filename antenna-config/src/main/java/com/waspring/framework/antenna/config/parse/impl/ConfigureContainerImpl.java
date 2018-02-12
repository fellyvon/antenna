package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureExceptions;
import com.waspring.framework.antenna.config.parse.IConfigureFilters;
import com.waspring.framework.antenna.config.parse.IConfigureIDGenerators;
import com.waspring.framework.antenna.config.parse.IConfigureInvokers;
import com.waspring.framework.antenna.config.parse.IConfigureProviders;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategys;
import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;

/**
 * Container配置管理
 * 
 * @author felly
 */
public class ConfigureContainerImpl extends AbstractConfiure implements IConfigureContainer {
	


	private IConfigureFilters configureFilters = null;
	private IConfigureExceptions configureExceptions = null;
	private IConfigureIDGenerators configureIDGenerators = null;
	private IConfigureRetryStrategys configureRetryStrategys = null;
	private IConfigureApplication application = null;
	private IConfigureProviders configureProviders = null;
	private IConfigureInvokers configureInvokers = null;

	@Override
	public IConfigureProviders getConfigureProviders() {
		return configureProviders;
	}

	@Override
	public IConfigureInvokers getConfigureInvokers() {
		return configureInvokers;
	}

	@Override
	public void setConfigureProviders(IConfigureProviders configureProviders) {
		this.configureProviders = configureProviders;
	}

	@Override
	public void setConfigureInvokers(IConfigureInvokers configureInvokers) {
		this.configureInvokers = configureInvokers;
	}

	@Override
	public String getElementName() {
		return ConfigureEnum.Container.getName();
	}

	@Override
	public boolean hasNext() {
		return configureProviders != null || configureInvokers != null;
	}

	@Override
	public IConfigureFilters getConfigureFilters() {

		return configureFilters;
	}

	@Override
	public void setConfigureFilters(IConfigureFilters configureFilters) {
		this.configureFilters = configureFilters;
	}

	@Override
	public IConfigureExceptions getConfigureExceptions() {
		return configureExceptions;
	}

	@Override
	public void setConfigureExceptions(IConfigureExceptions configureExceptions) {
		this.configureExceptions = configureExceptions;
	}

	@Override
	public IConfigureIDGenerators getConfigureIDGenerators() {
		return configureIDGenerators;
	}

	@Override
	public void setConfigureIDGenerators(IConfigureIDGenerators configureIDGenerators) {
		this.configureIDGenerators = configureIDGenerators;
	}

	@Override
	public long getTransferTimeout() {
		return Long.parseLong(getProperty(IConfigureContainer.TRANSFER_TIMEOUT));
	}
	@Override
	public String getLogMode() {
		return getProperty(IConfigureContainer.LOG_MODE);
	}

	@Override
	public String getContainerClass() {
		return getProperty(IConfigureContainer.CONTAINER_CLASS);
	}

	@Override
	public String getIDGenerator() {
		return getProperty(IConfigureContainer.ID_GENERATOR);
	}

	@Override
	public String getFilter() {
		return getProperty(IConfigureContainer.FILTER);
	}
	@Override
	public String getLogQueue() {
		return getProperty(IConfigureContainer.LOG_QUEUE);
	}

	@Override
	public IConfigureRetryStrategys getConfigureRetryStrategys() {
		return configureRetryStrategys;
	}

	@Override
	public void setConfigureRetryStrategys(IConfigureRetryStrategys configureRetryStrategys) {
		this.configureRetryStrategys = configureRetryStrategys;
	}

	public int getAllowMaximum() {
		try {
			return Integer.parseInt(getProperty(ALLOW_MAX_NUM));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return Integer.parseInt(getParent().getProperty(ALLOW_MAX_NUM));
			} catch (Exception ee) {
				ee.printStackTrace();
				return IVisitorContainer.ALLOW_MAX_NUM;
			}
		}
	}

	public ConfigureContainerImpl(IConfigureApplication application) {
		this.application = application;
	}

	@Override
	public IConfigure getParent() {
		return application;
	}

}
