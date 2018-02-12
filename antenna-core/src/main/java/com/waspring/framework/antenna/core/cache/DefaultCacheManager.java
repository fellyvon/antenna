package com.waspring.framework.antenna.core.cache;

import java.util.WeakHashMap;

/**
 * 缓存管理器默认实现，这里被实现为单利。<br/>
 * 如果期望通过spring这样的框架托管，那么可以继承AbstractCacheManager 使用createCache方法
 * 然后将对象用spring框架来托管
 * 
 * @author felly
 *
 */
public class DefaultCacheManager extends AbstractCacheManager {
	private static DefaultCacheManager defaultCache = null;

	/**
	 * 实例化
	 * 
	 * @return
	 */
	public static DefaultCacheManager getDefaultCacheManager() {
		if (defaultCache == null) {
			defaultCache = DefaultCacheManagerImpl.dcm;
		}
		return defaultCache;
	}

	/**
	 * 子类实例化实现单利
	 * 
	 *
	 */
	private static class DefaultCacheManagerImpl {
		private static final DefaultCacheManager dcm = new DefaultCacheManager();
	}

	/**
	 * 缓存对象的申请<br/>
	 * 注意到这里使用到了最终存放缓存数据的结构为WeakHashMap:<br/>
	 * 它的特殊之处在于 WeakHashMap 里的entry可能会被GC自动删除，<br/>
	 * 即使程序员没有调用remove()或者clear()方法。
	 * 
	 */
	@Override
	protected ICache createCache(String name) {

		return new MapCache<Object, Object>(name, new WeakHashMap<Object, Object>());
	}

}
