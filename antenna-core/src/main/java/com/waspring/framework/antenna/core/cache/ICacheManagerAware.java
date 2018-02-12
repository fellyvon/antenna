package com.waspring.framework.antenna.core.cache;

/**
 * 抽象具有缓存管理对象的能力
 * 
 * @author felly
 *
 */
public interface ICacheManagerAware {
	/**
	 * 设置缓存管理器
	 * @param cacheManager
	 */
	void setCacheManager(ICacheManager cacheManager);
}
