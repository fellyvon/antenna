package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureException;
import com.waspring.framework.antenna.config.parse.IConfigureExceptions;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 异常配置定义
 * @author felly
 *
 */
public class ConfigureExceptionImpl extends AbstractConfiure implements IConfigureException {
	private IConfigureExceptions exceptions = null;

	public ConfigureExceptionImpl(IConfigureExceptions exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public String getExceptionClass() {

		return getProperty(IConfigureException.EXCEPTION_CLASS);
	}

	@Override
	public IConfigure getParent() {

		return exceptions;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.Exception.getName();
	}

	@Override
	public boolean hasNext() {
		return false;
	}

}
