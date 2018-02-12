package com.waspring.framework.antenna.core.visitor;

/**
 * 访问异常
 * 
 * @author felly
 *
 */
public class VisitException extends Exception {

	public VisitException(String exp) {
		super(exp);
	}

	public VisitException(Exception exp) {
		super(exp);
	}

	public VisitException(Throwable exp) {
		super(exp);
	}
}
