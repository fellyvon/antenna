package com.waspring.framework.antenna.config.parse.impl;

import java.util.HashMap;
import java.util.Map;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureFilter;
import com.waspring.framework.antenna.config.parse.IConfigureFilters;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 过滤器组配置定义
 * 
 * @author felly
 *
 */
public class ConfigureFiltersImpl extends AbstractConfiure implements IConfigureFilters {
	private IConfigureContainer container = null;
	private Map<String, IConfigureFilter> filters = new HashMap<String, IConfigureFilter>();

	public ConfigureFiltersImpl(IConfigureContainer container) {
		super();
		this.container = container;
	}

	@Override
	public IConfigureFilter[] getConfigureFilter() {
		IConfigureFilter[] filterArray = new IConfigureFilter[filters.size()];
		return filters.values().toArray(filterArray);
	}

	@Override
	public void addConfigureFilter(IConfigureFilter configureFilter) {
		if (configureFilter == null) {
			return;
		}
		filters.put(configureFilter.getId(), configureFilter);
	}

	@Override
	public void removeConfigureFilter(IConfigureFilter configureFilter) {
		if (configureFilter == null) {
			return;
		}
		filters.remove(configureFilter.getId());
	}

	@Override
	public IConfigureFilter getConfigureFilter(String id) {
		return filters.get(id);
	}

	@Override
	public IConfigure getParent() {
		return container;
	}

	@Override
	public String getElementName() {
		return ConfigureEnum.Filters.getName();
	}
	@Override
	public boolean hasNext() {

		return !filters.isEmpty();
	}

}
