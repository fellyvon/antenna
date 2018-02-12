package com.waspring.framework.antenna.service;

import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 链式服务策略
 * 
 * @author felly
 *
 */
public class ServiceList extends ServiceCollection {

	public ServiceList() {

	}

	public ServiceList(IService... services) {
		super(services);
	}

	@Override
	public void service(IRequest request, IResponse response) throws Exception {
		IService[] services = getServices();
		if (services != null) {
			for (int i = 0; i < services.length; i++) {
				services[i].service(request, response);
				if (request.isServiced())///遇到已经被执行，直接返回
					return;
			}
		}

	}

}
