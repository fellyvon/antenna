package com.waspring.framework.antenna.core.hander;

import com.waspring.framework.antenna.core.service.ServiceException;

/**
 * 服务调用异常
 * 
 * @author felly
 *
 */
public class DemandException extends ServiceException {

	public DemandException(String exp) {
		super(exp);
	}

	public DemandException(Exception exp) {
		super(exp);
	}
}
