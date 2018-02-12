package com.waspring.framework.antenna.core.filter;

import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 过滤链
 * 
 * @author felly
 *
 */
public interface IFilterChain {

	void doFilter(IRequest request, IResponse response, IFilterChain chian);
}
