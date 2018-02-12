package com.waspring.framework.antenna.core.hander;

/**
 * 接收未知的异常，那么需要通过回滚方法来重置
 * 
 * @author felly
 *
 */
public class AcceptUnkownException extends AcceptException {

	public AcceptUnkownException(String exp) {
		super(exp);
	}

	public AcceptUnkownException(Exception exp) {
		super(exp);
	}
}
