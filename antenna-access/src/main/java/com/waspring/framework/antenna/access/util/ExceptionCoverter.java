package com.waspring.framework.antenna.access.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常类的转化，通过类路径，转化为对应的class.<br/>
 * 同时本类还缓存已配置指定的异常类,不会消亡！
 * 
 * @author felly
 *
 */
public abstract class ExceptionCoverter {

	private static Map<String, Class> throwables = new HashMap<String, Class>();
	static {
		registerException("exception",Exception.class);
	 
	}
	
	public static void registerException(String exception,Class expClass) {
		throwables.put(exception, expClass);
	}
	
	public static Class   getException(String exception) {
		return throwables.get(exception);
	}
	
}
