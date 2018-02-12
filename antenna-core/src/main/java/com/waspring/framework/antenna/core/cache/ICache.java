package com.waspring.framework.antenna.core.cache;

import java.util.Collection;
import java.util.Set;

/**
 * 缓存对象的定义
 * 
 * @author felly
 *
 * @param <K>
 *            键
 * @param <V>
 *            值
 */
public interface ICache<K, V> {

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key);

	/**
	 * 写入键值对，同时返回值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key, V value);

	/**
	 * 通过键移除值，同时返回移除的值
	 * 
	 * @param key
	 * @return
	 */
	public V remove(K key);

	/**
	 * 清空缓存
	 */

	public void clear();

	/**
	 * 当前键值对的总数
	 * 
	 * @return
	 */
	public int size();

	/**
	 * 返回不重复的全部键
	 * 
	 * @return
	 */
	public Set<K> keys();

	/**
	 * 返回全部缓存值
	 * 
	 * @return
	 */
	public Collection<V> values();
}
