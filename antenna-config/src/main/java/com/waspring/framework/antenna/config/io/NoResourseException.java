package com.waspring.framework.antenna.config.io;

/**
 * 找不到资源异常
 * 
 * @author felly
 *
 */
public class NoResourseException extends Exception {

	public NoResourseException(Exception e) {
		super(e);
	}

	public NoResourseException(String e) {
		super(e);
	}
}
