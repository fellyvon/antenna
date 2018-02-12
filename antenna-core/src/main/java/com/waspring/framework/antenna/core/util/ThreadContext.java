package com.waspring.framework.antenna.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.waspring.framework.antenna.core.visitor.IVisitorAware;

/**
 * 线程实例对象
 * 
 * @author felly
 *
 */
public class ThreadContext {

	private static final Logger log = Logger.getLogger(ThreadContext.class);

	public static final String VISITOR_KEY = ThreadContext.class.getName() + "_VISITOR_KEY";

	private static final ThreadLocal<Map<Object, Object>> resources = new InheritableThreadLocalMap<Map<Object, Object>>();

	protected ThreadContext() {
	}

	public static Map<Object, Object> getResources() {
		if (resources.get() == null) {
			return Collections.emptyMap();
		} else {
			return new HashMap<Object, Object>(resources.get());
		}
	}

	public static void setResources(Map<Object, Object> newResources) {
		if (newResources == null || newResources.isEmpty()) {
			return;
		}
		ensureResourcesInitialized();
		resources.get().clear();
		resources.get().putAll(newResources);
	}

	private static Object getValue(Object key) {
		Map<Object, Object> perThreadResources = resources.get();
		return perThreadResources != null ? perThreadResources.get(key) : null;
	}

	private static void ensureResourcesInitialized() {
		if (resources.get() == null) {
			resources.set(new HashMap<Object, Object>());
		}
	}

	public static Object get(Object key) {
		if (log.isTraceEnabled()) {
			String msg = "get() - in thread [" + Thread.currentThread().getName() + "]";
			log.trace(msg);
		}

		Object value = getValue(key);
		if ((value != null) && log.isTraceEnabled()) {
			String msg = "Retrieved value of type [" + value.getClass().getName() + "] for key [" + key + "] "
					+ "bound to thread [" + Thread.currentThread().getName() + "]";
			log.trace(msg);
		}
		return value;
	}

	public static void put(Object key, Object value) {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}

		if (value == null) {
			remove(key);
			return;
		}

		ensureResourcesInitialized();
		resources.get().put(key, value);

		if (log.isTraceEnabled()) {
			String msg = "Bound value of type [" + value.getClass().getName() + "] for key [" + key + "] to thread "
					+ "[" + Thread.currentThread().getName() + "]";
			log.trace(msg);
		}
	}

	public static Object remove(Object key) {
		Map<Object, Object> perThreadResources = resources.get();
		Object value = perThreadResources != null ? perThreadResources.remove(key) : null;

		if ((value != null) && log.isTraceEnabled()) {
			String msg = "Removed value of type [" + value.getClass().getName() + "] for key [" + key + "]"
					+ "from thread [" + Thread.currentThread().getName() + "]";
			log.trace(msg);
		}

		return value;
	}

	public static void remove() {
		resources.remove();
	}

	public static IVisitorAware getVisitor() {
		return (IVisitorAware) get(VISITOR_KEY);
	}

	public static IVisitorAware bind(IVisitorAware visitor) {
		if (visitor != null) {
			put(VISITOR_KEY, visitor);
		}
		return visitor;
	}

	public static IVisitorAware unbindVisitor() {
		return (IVisitorAware) remove(VISITOR_KEY);
	}

	private static final class InheritableThreadLocalMap<T extends Map<Object, Object>>
			extends InheritableThreadLocal<Map<Object, Object>> {

		@SuppressWarnings({ "unchecked" })
		protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
			if (parentValue != null) {
				return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
			} else {
				return null;
			}
		}
	}

}
