package com.waspring.framework.antenna.config.parse.impl;

/**
 * 无解析器异常
 * @author felly
 *
 */
public class NoParseException extends Exception {
	public NoParseException(Exception e) {
		super(e);
	}

	public NoParseException(String e) {
		super(e);
	}
}
