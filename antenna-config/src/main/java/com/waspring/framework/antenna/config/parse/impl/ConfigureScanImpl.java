package com.waspring.framework.antenna.config.parse.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureScan;
import com.waspring.framework.antenna.core.config.IConfigure;

/**
 * 扫描注解配置
 * 
 * @author felly
 *
 */
public class ConfigureScanImpl extends AbstractConfiure implements IConfigureScan {
	private List<String> scanPaths = new ArrayList<String>();

	@Override
	public String[] getScanPaths() {
		String[] paths = new String[scanPaths.size()];
		return scanPaths.toArray(paths);
	}

	@Override
	public void addScanPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return;
		}
		scanPaths.add(path);
	}

	@Override
	public void removeScanPath(String path) {
		scanPaths.remove(path);

	}

	private IConfigureApplication application = null;

	public ConfigureScanImpl(IConfigureApplication application) {
		this.application = application;
	}

	@Override
	public IConfigure getParent() {
		return application;
	}

	@Override
	public String getElementName() {
		return ConfigureEnum.Scan.getName();
	}

	@Override
	public boolean hasNext() {
		return false;
	}

 
}
