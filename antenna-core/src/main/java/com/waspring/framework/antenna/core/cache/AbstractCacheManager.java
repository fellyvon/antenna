package com.waspring.framework.antenna.core.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

import com.waspring.framework.antenna.core.Destoryable;
import com.waspring.framework.antenna.core.util.LifecycleUtils;

/**
 * 本地缓存管理器
 * 
 * @author felly
 *
 */
public abstract class AbstractCacheManager implements ICacheManager, Destoryable {

	///存放缓存对象
	private final ConcurrentMap<String, ICache> caches;

	public AbstractCacheManager() {
		this.caches = new ConcurrentHashMap<String, ICache>();
	}

	/**
	 * 从缓存对象中获取缓存值
	 */
	public <K, V> ICache<K, V> getCache(String name)  {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Cache name cannot be null or empty.");
		}

		ICache cache;

		cache = caches.get(name);
		if (cache == null) {
			cache = createCache(name);
			ICache existing = caches.putIfAbsent(name, cache);
			if (existing != null) {
				cache = existing;
			}
		}

		// noinspection unchecked
		return cache;
	}

	/**
	 * 获取缓存对象，留给子类来实现
	 * @param name
	 * @return
	 */
	protected abstract ICache createCache(String name)  ;

	/**
	 * 销毁缓存对象
	 */
	public void destory() {
		while (!caches.isEmpty()) {
			for (ICache cache : caches.values()) {
				LifecycleUtils.destory(cache);
			}
			caches.clear();
		}
	}

	
	public String toString() {
		Collection<ICache> values = caches.values();
		StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append(" with ").append(caches.size())
				.append(" cache(s)): [");
		int i = 0;
		for (ICache cache : values) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(cache.toString());
			i++;
		}
		sb.append("]");
		return sb.toString();
	}
}
