package com.waspring.framework.antenna.config.parse;

import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.core.hander.IHander;

/**
 * 为整个配置读取的入口
 * 
 * @author felly
 *
 */
public interface IConfigureApplication extends IConfigure {
	String CONFIGURE_CACHE_KEY = IConfigureApplication.class.getName() + "_CONFIGURE_CACHE_KEY";

	IConfigureContainer[] getConfigureContainer();

	IConfigureContainer getConfigureContainer(String id);

	void addConfigureContainer(IConfigureContainer configureContainer);

	void removeConfigureContainer(IConfigureContainer configureContainer);

	void setConfigureContainer(IConfigureContainer[] configureContainers);

	IConfigureApplication[] getImportConfigureApplications();

	void setConfigureApplication(IConfigureApplication[] configureApplication);

	IConfigureScan getConfigScan();

	IConfigure[] getExtendConfigure();

	void addExtendConfigure(IConfigure configure);

	void removeExcetendConfigure(IConfigure configure);

	IConfigureImport[] getConfigureImport();

	void addConfigureImport(IConfigureImport configure);

	void removeConfigureImport(IConfigureImport configure);

	IConfigureInvoker getIConfigInvoker(String containerId, String action);

	IConfigureProvider getIConfigProvider(String containerId, String action);

	IConfigure    getConfigureHander(String containerId, String action);
	
	
	IConfigureQueueManager getConfigureQueueManager();
	void setConfigureQueueManager(IConfigureQueueManager configureQueueManager);
	
	String inverse();
	
}
