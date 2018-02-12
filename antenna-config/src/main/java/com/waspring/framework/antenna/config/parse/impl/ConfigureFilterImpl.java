package com.waspring.framework.antenna.config.parse.impl;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureFilter;
import com.waspring.framework.antenna.config.parse.IConfigureFilters;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 过滤器配置
 * 
 * @author felly
 *
 */
public class ConfigureFilterImpl extends AbstractConfiure implements IConfigureFilter {
	private IConfigureFilters filters = null;

	public ConfigureFilterImpl(IConfigureFilters filters) {
		this.filters = filters;
	}

	@Override
	public String getFilterClass() {

		return getProperty(IConfigureFilter.FILTER_CLASS);
	}

	@Override
	public IConfigure getParent() {

		return filters;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.Filter.getName();
	}

	@Override
	public boolean hasNext() {
		return false;
	}

}
