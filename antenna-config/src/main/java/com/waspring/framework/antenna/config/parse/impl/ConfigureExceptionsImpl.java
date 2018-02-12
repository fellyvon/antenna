package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureException;
import com.waspring.framework.antenna.config.parse.IConfigureExceptions;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 异常组配置定义
 * 
 * @author felly
 *
 */
public class ConfigureExceptionsImpl extends AbstractConfiure implements IConfigureExceptions {
	private IConfigureContainer container = null;

	public ConfigureExceptionsImpl(IConfigureContainer container) {
		super();
		this.container = container;
	}

	@Override
	public IConfigureException[] getConfigureException() {
		IConfigureException[] configureExceptions = new IConfigureException[exceptions.size()];
		return exceptions.values().toArray(configureExceptions);
	}

	@Override
	public void addConfigureException(IConfigureException exceptionConfig) {
		if (exceptionConfig == null) {
			return;
		}
		exceptions.put(exceptionConfig.getId(), exceptionConfig);

	}

	@Override
	public void removeConfigureException(IConfigureException exceptionConfig) {
		if (exceptionConfig == null) {
			return;
		}
		exceptions.remove(exceptionConfig.getId());
	}

	@Override
	public IConfigureException getConfigureException(String id) {

		return exceptions.get(id);
	}

	private Map<String, IConfigureException> exceptions = new HashMap<String, IConfigureException>();

	@Override
	public IConfigure getParent() {

		return container;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.Exceptions.getName();
	}

	@Override
	public boolean hasNext() {

		return !exceptions.isEmpty();
	}

}
