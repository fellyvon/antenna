package com.waspring.framework.antenna.access.manager;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.access.IConfigBeanFactory;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.visitor.IVisitorContainer;
import com.waspring.framework.antenna.core.visitor.IVisitorContainerFactory;

/**
 * container工厂，实际上是委托IConfigBeanFactory 来处理！<br/>
 * 考虑到初始化的性能消耗，使用本地缓存对已初始化对象进行了缓存处理
 * 
 * 
 * @author felly
 *
 */
public class VisitorContainerFactory implements IVisitorContainerFactory {
	public static final String VISITOR_CONTAINER_CACHE_NAME = "_VISITOR_CONTAINER_CACHE_NAME_"
			+ VisitorContainerFactory.class.getName();
	protected Logger log = Logger.getLogger(getClass());
	private IApplication application;
	private IConfigBeanFactory<IVisitorContainer> configBeanFactory = null;
	private ICache<String, IVisitorContainer> cache = null;

	@SuppressWarnings("unchecked")
	public VisitorContainerFactory(IApplication application) {
		this.application = application;
		configBeanFactory = new VisitorContainerConfigBeanFactory<IVisitorContainer>(application, this);
		cache = application.<String, IVisitorContainer>getCache(VisitorContainerFactory.VISITOR_CONTAINER_CACHE_NAME);
	}

	/**
	 * 防止并发产生很多的容器对象
	 */
	@Override
	public synchronized IVisitorContainer createVisitorContainer(String containerId) {
		IVisitorContainer container = cache.get(containerId);
		if (container != null) {
			LogUtil.debug(log, " 从缓存获取到已初始化容器:" + container.getId());
			return container;
		}
		container = configBeanFactory.create(containerId);

		cache.put(containerId, container);
		LogUtil.debug(log, "产生容器对象:" + container.getId() + "并计入缓存");
		return container;
	}
}
