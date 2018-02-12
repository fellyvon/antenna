package com.waspring.framework.antenna.core.cache;


/**
 * 缓存管理器抽象
 * @author felly
 *
 */
public interface  ICacheManager {

	
	/**
	 * 通过缓存名获取缓存存储对象
	 * @param cacheName
	 * @return
	 */
	  public <K, V> ICache<K, V> getCache(String cacheName)  ; 
}
