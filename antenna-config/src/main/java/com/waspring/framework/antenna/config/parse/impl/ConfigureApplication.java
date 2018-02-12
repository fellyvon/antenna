package com.waspring.framework.antenna.config.parse.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.waspring.framework.antenna.config.parse.ConfigureEnum;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureImport;
import com.waspring.framework.antenna.config.parse.IConfigureInvoker;
import com.waspring.framework.antenna.config.parse.IConfigureInvokers;
import com.waspring.framework.antenna.config.parse.IConfigureProvider;
import com.waspring.framework.antenna.config.parse.IConfigureProviders;
import com.waspring.framework.antenna.config.parse.IConfigureQueue;
import com.waspring.framework.antenna.config.parse.IConfigureQueueManager;
import com.waspring.framework.antenna.config.parse.IConfigureScan;
import com.waspring.framework.antenna.core.config.IConfigure;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.util.VisitorUtil;

/**
 * 配置入口实现
 * 
 * @author felly
 *
 */
public class ConfigureApplication extends AbstractConfiure implements IConfigureApplication {
	private StringBuilder docInfo = new StringBuilder();

	public void setRootDocument(String xml) {
		docInfo.append(xml);
	}

	@Override
	public String inverse() {
		return docInfo.toString();
	}

	private IConfigureQueueManager configureQueueManager = null;//// 隊列管理器

	@Override
	public IConfigureQueueManager getConfigureQueueManager() {
		return configureQueueManager;
	}

	public void setConfigureQueueManager(IConfigureQueueManager configureQueueManager) {
		this.configureQueueManager = configureQueueManager;
	}

	@Override
	public IConfigure getConfigureHander(String containerId, String action) {

		if (VisitorUtil.getVisitor().getRequest().isServer()) {
			return getIConfigProvider(containerId, action);

		} else {
			return getIConfigInvoker(containerId, action);
		}

	}

	@Override
	public IConfigureInvoker getIConfigInvoker(String containerId, String action) {
		IConfigureContainer container = getConfigureContainer(containerId);
		if (container != null) {
			IConfigureInvokers invokers = container.getConfigureInvokers();
			if (invokers != null) {
				return invokers.getIConfigureInvoker(action);
			}
		}
		return null;
	}

	@Override
	public IConfigureProvider getIConfigProvider(String containerId, String action) {
		IConfigureContainer container = getConfigureContainer(containerId);
		if (container != null) {
			IConfigureProviders providers = container.getConfigureProviders();
			if (providers != null) {
				return providers.getConfigureProvider(action);
			}
		}
		return null;
	}

	private Map<String, IConfigure> extendsConfiures = new HashMap<String, IConfigure>();

	private List<IConfigureImport> imports = new ArrayList<IConfigureImport>();

	@Override
	public IConfigureImport[] getConfigureImport() {
		IConfigureImport[] importArray = new IConfigureImport[imports.size()];

		return imports.toArray(importArray);
	}

	@Override
	public void addConfigureImport(IConfigureImport configure) {
		if (configure == null) {
			return;
		}
		imports.add(configure);
	}

	@Override
	public void removeConfigureImport(IConfigureImport configure) {
		imports.remove(configure);
	}

	@Override
	public IConfigure[] getExtendConfigure() {
		IConfigure[] configs = new IConfigure[extendsConfiures.size()];
		return extendsConfiures.values().toArray(configs);
	}

	@Override
	public void addExtendConfigure(IConfigure configure) {
		if (configure == null) {
			return;
		}
		extendsConfiures.put(configure.getId(), configure);
	}

	@Override
	public void removeExcetendConfigure(IConfigure configure) {
		if (configure == null) {
			return;
		}
		extendsConfiures.remove(configure.getId());
	}

	private Map<String, IConfigureContainer> configureContainers = new HashMap<String, IConfigureContainer>();
	private List<IConfigureApplication> importConfigures = new ArrayList<IConfigureApplication>();
	private IConfigureScan scan;

	@Override
	public IConfigureContainer[] getConfigureContainer() {
		IConfigureContainer[] subConfigureContainers = new IConfigureContainer[configureContainers.size()];
		return configureContainers.values().toArray(subConfigureContainers);
	}

	@Override
	public IConfigureContainer getConfigureContainer(String id) {

		return configureContainers.get(id);
	}

	@Override
	public void addConfigureContainer(IConfigureContainer configureContainer) {
		if (configureContainer == null) {
			return;
		}
		configureContainers.put(configureContainer.getId(), configureContainer);
	}

	@Override
	public void removeConfigureContainer(IConfigureContainer configureContainer) {
		if (configureContainer == null) {
			return;
		}
		configureContainers.remove(configureContainer.getId());
	}

	@Override
	public void setConfigureContainer(IConfigureContainer[] configureContainers) {
		if (configureContainers == null || configureContainers.length == 0) {
			return;
		}
		for (IConfigureContainer cc : configureContainers) {
			addConfigureContainer(cc);
		}
	}

	@Override
	public IConfigureApplication[] getImportConfigureApplications() {
		IConfigureApplication[] cas = new IConfigureApplication[importConfigures.size()];
		return importConfigures.toArray(cas);
	}

	@Override
	public void setConfigureApplication(IConfigureApplication[] configureApplication) {
		if (configureApplication == null || configureApplication.length == 0) {
			return;
		}
		for (IConfigureApplication ca : configureApplication) {

			if (importConfigures.contains(ca)) {
				/// 防止重复添加
				continue;
			}

			importConfigures.add(ca);
			IConfigureContainer ccs[] = ca.getConfigureContainer();
			if (ccs == null || ccs.length == 0) {
				continue;
			}
			for (IConfigureContainer cc : ccs) {
				IConfigureContainer cur = getConfigureContainer(cc.getId());
				if (cur != null) {// 存在相同配置跳过
					LogUtil.warn(log, "import配置中id为" + cur.getId() + "的container已经存在，跳过加载！");
					continue;
				}
				addConfigureContainer(cc);

			}
			String[] path = ca.getConfigScan().getScanPaths();
			if (path != null && path.length > 0) {
				for (String p : path) {
					getConfigScan().addScanPath(p);
				}

			}

			IConfigureQueueManager configureQueueManager = ca.getConfigureQueueManager();

			if (configureQueueManager != null) {
				IConfigureQueue[] configureQueues = configureQueueManager.getIConfigureQuques();
				if (configureQueues != null) {
					for (IConfigureQueue cq : configureQueues) {
						///
						IConfigureQueue curConfigureQueue = getConfigureQueueManager().getIConfigureQuque(cq.getId());
						if (curConfigureQueue != null) {// 存在相同配置跳过
							LogUtil.warn(log, "import配置中id为" + curConfigureQueue.getId() + "的queue已经存在，跳过加载！");
							continue;
						}
						getConfigureQueueManager().addConfigureQuque(cq);
					}
				}

			}

		}

	}

	@Override
	public IConfigureScan getConfigScan() {
		if (scan == null) {
			scan = new ConfigureScanImpl(this);
		}
		return scan;
	}

	public IConfigureScan getScan() {
		return scan;
	}

	public void setScan(IConfigureScan scan) {
		this.scan = scan;
	}

	@Override
	public IConfigure getParent() {
		return null;
	}

	@Override
	public String getElementName() {

		return ConfigureEnum.Application.getName();
	}

	@Override
	public boolean hasNext() {
		return !configureContainers.isEmpty() || scan.getScanPaths().length > 0;
	}

}
