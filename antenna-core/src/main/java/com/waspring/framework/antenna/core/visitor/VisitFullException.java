package com.waspring.framework.antenna.core.visitor;

/**
 * 容量满
 * 
 * @author felly
 *
 */
public class VisitFullException extends VisitException {

	public VisitFullException(String exp) {
		super(exp);
	}

	public VisitFullException(Exception exp) {
		super(exp);
	}

	public VisitFullException(Throwable exp) {
		super(exp);
	}
}
