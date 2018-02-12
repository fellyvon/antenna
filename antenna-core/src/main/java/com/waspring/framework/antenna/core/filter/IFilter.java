package com.waspring.framework.antenna.core.filter;

import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 过滤器
 * 
 * @author felly
 *
 */
public interface IFilter {

	void doFilter(IRequest request, IResponse response);
	
	
}
