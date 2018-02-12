package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureIDGenerator;
import com.waspring.framework.antenna.config.parse.IConfigureIDGenerators;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 请求Id生成类配置的容器实现
 * 
 * @author felly
 *
 */
public class ConfigureIDGeneratorsImpl extends AbstractConfiure implements IConfigureIDGenerators {
	private Map<String, IConfigureIDGenerator> configureIDGenerators = new HashMap<String, IConfigureIDGenerator>();
	private IConfigureContainer container = null;

	public ConfigureIDGeneratorsImpl(IConfigureContainer container) {
		this.container = container;
	}

	@Override
	public void addConfigureIDGenerators(IConfigureIDGenerator idConfigure) {
		configureIDGenerators.put(idConfigure.getId(), idConfigure);
	}

	@Override
	public IConfigureIDGenerator getConfigureIDGenerator(String id) {

		return configureIDGenerators.get(id);
	}

	@Override
	public void removeConfigureIDGenerator(IConfigureIDGenerator idConfigure) {
		if (idConfigure == null) {
			return;
		}
		configureIDGenerators.remove(idConfigure.getId());

	}

	@Override
	public IConfigureIDGenerator[] getIConfigureIDGenerators() {
		IConfigureIDGenerator[] configureIDGeneratorArray = new IConfigureIDGenerator[configureIDGenerators.size()];
		return configureIDGenerators.values().toArray(configureIDGeneratorArray);
	}

	@Override
	public IConfigure getParent() {

		return container;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.IDGenerators.getName();
	}

	@Override
	public boolean hasNext() {

		return configureIDGenerators != null && !configureIDGenerators.isEmpty();
	}

}
