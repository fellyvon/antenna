package com.waspring.framework.antenna.core.visitor;

import com.waspring.framework.antenna.core.IApplication;
import com.waspring.framework.antenna.core.hander.IHander;

public interface IVisitor {
	IRequest getRequest();

	@SuppressWarnings("rawtypes")
	IResponse getResponse();

	IHander getHander();

	long getTimestamp();

	IVisitorContainer getVisitorContainer();
	String getId();
	
	IApplication getApplicaiton();
}
