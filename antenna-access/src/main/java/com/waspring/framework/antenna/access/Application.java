package com.waspring.framework.antenna.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.access.manager.VisitorContainerFactory;
import com.waspring.framework.antenna.access.util.ApplicationUtil;
import com.waspring.framework.antenna.config.Configuration;
import com.waspring.framework.antenna.config.IConfiguration;
import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.cache.DefaultCacheManager;
import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.cache.ICacheManager;
import com.waspring.framework.antenna.core.filter.IFilter;
import com.waspring.framework.antenna.core.hander.IFilterFactory;
import com.waspring.framework.antenna.core.hander.IHanderFactory;
import com.waspring.framework.antenna.core.hander.IProvider;
import com.waspring.framework.antenna.core.hander.Invoker;
import com.waspring.framework.antenna.core.util.LifecycleUtils;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.IVisitorContainerFactory;
import com.waspring.framework.antenna.preservation.queue.IQueueManger;

/**
 * 本框架的入口
 * 
 * @author felly
 *
 */
public abstract class Application implements IApplication, IManagerObtain {

	public static final String APPLICATION_CACHE_NAME = "_application_cache_" + Application.class.getName();
	public static final String APPLICATION_CACHE_KEY = "_application_key_" + Application.class.getName();

	private String configFilePath;
	private static ICacheManager cacheManager = null;
	private InvokerFactory invokerFactory = null;
	private IQueueManagerFactory queueManagerFactory = null;
	private IFilterFactory filterFactory = null;
	private ProviderFactory providerFactory = null;
	private IVisitorContainerFactory visitorFactory = null;
	private IConfigureApplication configureApplication = null;
	private IConfiguration configuration = null;
	private List<IVisitorContainer> containers = new ArrayList<IVisitorContainer>();
	private boolean isInit = false;

	@Override
	public  void init() throws Exception {
		if (isInit) {
			return;
		}
		try {
			modeState();
			if (StringUtils.isEmpty(getConfigFilePath())) {
				throw new RuntimeException("at " + Application.class.getName() + ",ConfigFilePath can't be empty!");
			}
			if (configuration == null) {
				System.out.println("加载：" + getConfigFilePath());
				configuration = new Configuration(getConfigFilePath());
				configureApplication = configuration.getConfigureApplication();
				///
				// qm = new BlockQueueManager(configureApplication.getConfigureQueueManager());
			}
			invokerFactory = new InvokerFactory(this);
			providerFactory = new ProviderFactory(this);
			visitorFactory = new VisitorContainerFactory(this);
			filterFactory = new FilterFactory(this);
			queueManagerFactory = new QueueManagerFactory(this);
		} finally {
			isInit = true;
		}

	}

	public static synchronized IApplication getApplication() {
		if (cacheManager == null) {
			cacheManager = DefaultCacheManager.getDefaultCacheManager();
		}
		IApplication application = null;
		ICache<String, IApplication> cache = null;
		cache = cacheManager.getCache(APPLICATION_CACHE_NAME);
		application = (IApplication) cache.get(APPLICATION_CACHE_KEY);
		if (application == null) {
			application = new Application() {
			};
			cache.put(APPLICATION_CACHE_KEY, application);
		}

		return application;
	}

	private Application() {
	}

	public static ICacheManager getCacheManager() {
		return cacheManager;
	}

	@Override
	public Queue getQueue(String queueId) {
		IQueueManger qm = null;

		qm = queueManagerFactory.getQueueManger();
		return qm.applyQueue(queueId);

	}

	private void modeState() {
		if (ApplicationUtil.devloperMode) {
			System.out.println("当前模式：调试模式,传输验证、时效验证都不会开启！");
		} else {
			System.out.println("当前模式：正常模式,传输验证、时效验证都已生效！");
		}
	}

	@Override
	public void destory() {
		/// 结束队列的生命
		queueManagerFactory.getQueueManger().stop(true);
		/// 结束缓存的生命
		LifecycleUtils.destory(cacheManager);
		////// 结束配置管理器的生命
		LifecycleUtils.destory(configuration);

	}

	@Override
	public <K, V> ICache<K, V> getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}

	@Override
	public String getConfigFilePath() {
		return configFilePath;
	}

	@Override
	public IApplication setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
		return this;
	}

	@Override
	public IVisitorContainer applyContainer(String containerId) {
		IVisitorContainer container = visitorFactory.createVisitorContainer(containerId);
		if (container != null) {
			if (!containers.contains(container)) {
				containers.add(container);
			}
		}
		return container;
	}

	@Override
	public IFilter getFilter(IVisitorContainer container, String filterId) {
		return filterFactory.createFilter(container.getId(), filterId);
	}

	@Override
	public Invoker getInvoker(IVisitorContainer container, String action) {
		return invokerFactory.createHander(container.getId(), action);
	}

	@Override
	public IVisitorContainer[] getVisitorContainers() {
		IVisitorContainer[] containerArray = new IVisitorContainer[containers.size()];
		return containers.toArray(containerArray);
	}

	@Override
	public IVisitorContainer getCurrentVisitorContainer() {
		IVisitorContainer containers[] = getVisitorContainers();
		if (containers != null && containers.length > 0) {
			return containers[0];
		}
		return null;
	}

	@Override
	public IProvider getProvider(IVisitorContainer container, String action) {
		return providerFactory.createHander(container.getId(), action);
	}

	public IVisitorContainerFactory getVisitorFactory() {
		return visitorFactory;
	}

	public void setVisitorFactory(IVisitorContainerFactory visitorFactory) {
		this.visitorFactory = visitorFactory;
	}

	public IConfigureApplication getConfigureApplication() {
		return configureApplication;
	}

	public void setConfigureApplication(IConfigureApplication configureApplication) {
		this.configureApplication = configureApplication;
	}

	public IConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(IConfiguration configuration) {
		this.configuration = configuration;
	}

	public void setInvokerFactory(InvokerFactory invokerFactory) {
		this.invokerFactory = invokerFactory;
	}

	public void setProviderFactory(ProviderFactory providerFactory) {
		this.providerFactory = providerFactory;
	}

	@Override
	public IHanderFactory getInvokerFactory() {

		return invokerFactory;
	}

	@Override
	public IHanderFactory getProviderFactory() {

		return providerFactory;
	}

	@Override
	public IConfigureApplication getRootConfigure() {
		return configureApplication;
	}

	public static void setCacheManager(ICacheManager cacheManager) {
		Application.cacheManager = cacheManager;
	}

	public IQueueManagerFactory getQueueManagerFactory() {
		return queueManagerFactory;
	}

	public void setQueueManagerFactory(IQueueManagerFactory queueManagerFactory) {
		this.queueManagerFactory = queueManagerFactory;
	}

	public IFilterFactory getFilterFactory() {
		return filterFactory;
	}

	public void setFilterFactory(IFilterFactory filterFactory) {
		this.filterFactory = filterFactory;
	}

	public List<IVisitorContainer> getContainers() {
		return containers;
	}

	public void setContainers(List<IVisitorContainer> containers) {
		this.containers = containers;
	}

}
