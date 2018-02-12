package com.waspring.framework.antenna.config.parse.impl;

/**
 * 重复ID
 * @author felly
 *
 */
public class RepeatIDException extends Exception {
	public RepeatIDException(Exception e) {
		super(e);
	}

	public RepeatIDException(String e) {
		super(e);
	}
}
