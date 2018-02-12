package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureIDGenerator;
import com.waspring.framework.antenna.config.parse.IConfigureIDGenerators;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * ID序列生成器
 * 
 * @author felly
 *
 */
public class ConfigureIDGeneratorImpl extends AbstractConfiure implements IConfigureIDGenerator {

	private IConfigureIDGenerators configureIDGenerators = null;

	public ConfigureIDGeneratorImpl(IConfigureIDGenerators configureIDGenerators) {
		this.configureIDGenerators = configureIDGenerators;
	}

	@Override
	public String getGeneratorClass() {

		return super.getProperty(IConfigureIDGenerator.GENERATOR_CLASS);
	}

	@Override
	public IConfigure getParent() {

		return configureIDGenerators;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.IDGenerator.getName();
	}

	@Override
	public boolean hasNext() {

		return false;
	}

}
