package com.waspring.framework.antenna.core.service;

/**
 * 服务执行异常
 * 
 * @author felly
 *
 */
public class ServiceException extends Exception {

	public ServiceException(String exp) {
		super(exp);
	}

	public ServiceException(Exception exp) {
		super(exp);
	}

	public ServiceException(Throwable exp) {
		super(exp);
	}
}
