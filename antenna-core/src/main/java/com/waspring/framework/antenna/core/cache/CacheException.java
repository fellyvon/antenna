package com.waspring.framework.antenna.core.cache;

/**
 * 缓存相关的异常定义
 * @author felly
 *
 */
public class CacheException extends Exception {

	public CacheException() {
		super();
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}
}
