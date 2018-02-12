package com.waspring.framework.antenna.core.hander;

import com.waspring.framework.antenna.core.service.ServiceException;

/**
 * 服务受理异常
 * 
 * @author felly
 *
 */
public class AcceptException extends ServiceException {

	public AcceptException(String exp) {
		super(exp);
	}

	public AcceptException(Exception exp) {
		super(exp);
	}
}
