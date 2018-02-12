package com.waspring.framework.antenna.core.visitor;

/**
 * 释放失败
 * 
 * @author felly
 *
 */
public class FailReleaseException extends VisitException {

	public FailReleaseException(String exp) {
		super(exp);
	}

	public FailReleaseException(Exception exp) {
		super(exp);
	}

	public FailReleaseException(Throwable exp) {
		super(exp);
	}
}
