package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategy;
import com.waspring.framework.antenna.config.parse.IConfigureRetryStrategys;
import com.waspring.framework.antenna.core.config.IConfigure;

public class ConfigureRetryStrategysImpl extends AbstractConfiure implements IConfigureRetryStrategys {
	@Override
	public IConfigureRetryStrategy getConfigureRetryStrategy(String id) {
		return strategys.get(id);
	}

	private IConfigureContainer container = null;

	public ConfigureRetryStrategysImpl(IConfigureContainer container) {
		super();
		this.container = container;
	}

	private Map<String, IConfigureRetryStrategy> strategys = new HashMap<String, IConfigureRetryStrategy>();

	@Override
	public IConfigureRetryStrategy[] getConfigureRetryStrategys() {
		IConfigureRetryStrategy[] crs = new IConfigureRetryStrategy[strategys.size()];
		return strategys.values().toArray(crs);
	}

	@Override
	public void addConfigureRetryStrategy(IConfigureRetryStrategy strategy) {
		String id = strategy.getId();
		if (StringUtils.isEmpty(id)) {
			throw new RuntimeException("strategy is not exists id!");
		}
		IConfigureRetryStrategy strategyItem = strategys.get(id);
		if (strategyItem != null) {
			throw new RuntimeException("strategy 'Id is duplicate!");
		}

		strategys.put(id, strategy);

	}

	@Override
	public void removeConfigureRetryStrategy(IConfigureRetryStrategy strategy) {
		String id = strategy.getId();
		if (StringUtils.isEmpty(id)) {
			return;
		}
		strategys.remove(strategy.getId());
	}

	@Override
	public IConfigure getParent() {

		return container;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.RetryStrategys.getName();
	}

	@Override
	public boolean hasNext() {

		return strategys.isEmpty();
	}

}
