package com.waspring.framework.antenna.client;

import com.waspring.framework.antenna.core.filter.IFilter;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * demo-过滤器
 * @author felly
 *
 */
public class FirstFilter implements IFilter {

	@Override
	public void doFilter(IRequest request, IResponse response) {
	   String action=request.getAction();
	   System.out.println("action="+action+",be filtered！");

	}

}
