package com.waspring.framework.antenna.access;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.cache.ICache;
import com.waspring.framework.antenna.core.filter.IFilter;
import com.waspring.framework.antenna.core.hander.IFilterFactory;

/**
 * 过滤器创建工厂
 * 
 * @author felly
 *
 */
public class FilterFactory implements IFilterFactory {
	private IConfigBeanFactory<IFilter> beanFactory = null;
	public static final String FITER_CACHE_NAME = "_FITER_CACHE_NAME_" + FilterFactory.class.getName();

	private Logger log = Logger.getLogger(getClass());
	private IApplication application = null;
	private ICache<HanderKey, IFilter> cache = null;

	public FilterFactory(IApplication application) throws Exception {
		this.application = application;
		beanFactory = new FilterConfigBeanFactory<IFilter>(application, this);
		cache = application.<HanderKey, IFilter>getCache(FilterFactory.FITER_CACHE_NAME);
	}

	public IConfigBeanFactory<IFilter> getBeanFactory() {
		return beanFactory;
	}

 

	public void setBeanFactory(IConfigBeanFactory<IFilter> beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * 创建key
	 */
	private HanderKey bornKey(String containerId, String action) {

		return new HanderKey(containerId, action);
	}

	@Override
	public  IFilter createFilter(String containerId, String id) {
		if(StringUtils.isEmpty(id)) {
			return null;
		}
		HanderKey key = bornKey(containerId, id);
		return getBeanFactory().create(key);

	}

}
