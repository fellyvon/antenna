package com.waspring.framework.antenna.access;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.config.parse.IConfigureApplication;
import com.waspring.framework.antenna.config.parse.IConfigureContainer;
import com.waspring.framework.antenna.config.parse.IConfigureFilter;
import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.filter.IFilter;
import com.waspring.framework.antenna.core.hander.IFilterFactory;
import com.waspring.framework.antenna.core.util.LogUtil;
import com.waspring.framework.antenna.core.util.ReflectUtil;

/**
 * 从配置数据中创建Filter
 * 
 * @author felly
 *
 */
@SuppressWarnings("rawtypes")
public class FilterConfigBeanFactory<T extends IFilter> implements IConfigBeanFactory {
	private Logger log = Logger.getLogger(FilterConfigBeanFactory.class);
	private IApplication application = null;
	private IFilterFactory filterFactory = null;
	private ICache<HanderKey, T> cache = null;

	public FilterConfigBeanFactory(IApplication application, IFilterFactory filterFactory) {
		this.application = application;
		this.filterFactory = filterFactory;
	
	}

	/**
	 * TODO缓存Hander
	 */
	public synchronized T create(java.io.Serializable id) {

		HanderKey key = (HanderKey) id;
		T filter = cache.get(key);
		if (filter != null) {
			return filter;
		}
		String containerId = key.getContainerId();
		String filterId = key.getAction();
		IConfigureApplication configure = (IConfigureApplication) application.getRootConfigure();
		if (configure != null) {

			/// TODO
			IConfigureContainer cc = configure.getConfigureContainer(containerId);
			if (cc != null) {
				IConfigureFilter filterConfigure = cc.getConfigureFilters().getConfigureFilter(filterId);
				if (filterConfigure != null) {
					String filterClass = filterConfigure.getFilterClass();
					try {
						filter =  ReflectUtil.<T>constructorNewInstance(filterClass, new Class[] {},
								new Object[] {});
						cache.put(key, filter);
					} catch (Exception e) {
						e.printStackTrace();
						LogUtil.error(log, e);
					}

				}
			}

		}

		return filter;

	}

	public static final String FILTER_CACHE_NAME = "_FILTER_CACHE_NAME_";

}
