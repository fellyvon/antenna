package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureImport;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 扫描注解配置
 * 
 * @author felly
 *
 */
public class ConfigureImportImpl extends AbstractConfiure implements IConfigureImport {
	@Override
	public String path() {
		return getProperty(PATH);
	}

	private IConfigureApplication application = null;

	public ConfigureImportImpl(IConfigureApplication application) {
		this.application = application;
	}

	@Override
	public IConfigure getParent() {
		return application;
	}

	@Override
	public String getElementName() {
		return ConfigureEnum.Import.getName();
	}

	@Override
	public boolean hasNext() {
		return false;
	}

}
