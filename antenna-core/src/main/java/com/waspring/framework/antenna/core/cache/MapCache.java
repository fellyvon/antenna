package com.waspring.framework.antenna.core.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 默认的缓存实现
 * 
 * @author felly
 *
 */
public class MapCache<K, V> implements ICache<K, V> {

	
	private final Map<K, V> map; //实际存储数据的结构

	private final String name;//缓存对象名

	/**
	 * 构造
	 * @param name 缓存对象名
	 * @param backingMap  实际存储数据的结构
	 */
	public MapCache(String name, Map<K, V> backingMap) {
		if (name == null) {
			throw new IllegalArgumentException("Cache name cannot be null.");
		}
		if (backingMap == null) {
			throw new IllegalArgumentException("Backing map cannot be null.");
		}
		this.name = name;
		this.map = backingMap;
	}

	public V get(K key)  {
		return map.get(key);
	}

	public V put(K key, V value)  {
		return map.put(key, value);
	}

	public V remove(K key)   {
		return map.remove(key);
	}

	public void clear()  {
		map.clear();
	}

	public int size() {
		return map.size();
	}

	public Set<K> keys() {
		Set<K> keys = map.keySet();
		if (!keys.isEmpty()) {
			return Collections.unmodifiableSet(keys);
		}
		return Collections.emptySet();
	}

	public Collection<V> values() {
		Collection<V> values = map.values();
		if (!values.isEmpty()) {
			return Collections.unmodifiableCollection(values);
		}
		return Collections.emptySet();
	}

	public String toString() {
		return new StringBuilder("MapCache '").append(name).append("' (").append(map.size()).append(" entries)")
				.toString();
	}

}
