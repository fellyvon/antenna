package com.waspring.framework.antenna.service;

import java.util.Arrays;
import java.util.List;

import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.service.IServiceContainer;
import com.waspring.framework.antenna.core.service.ServiceException;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 服务包装策略，保护实际的服务类
 * 
 * @author felly
 *
 */
public class ServiceWrapper extends AbstractServiceContainer {
	protected IService service;

	public IService getService() {
		return service;
	}

	@Override
	public IService[] getServices() {
		if (service == null)
			return new IService[0];
		return new IService[] { service };
	}

	/* ------------------------------------------------------------ */
	/**
	 * 设置一个服务
	 */
	public void setService(IService service) {

		// check for loops
		if (service == this || (service instanceof IServiceContainer
				&& Arrays.asList(((IServiceContainer) service).getChildServices()).contains(this)))
			throw new IllegalStateException("setHandler loop");

		this.service = service;

	}

	/**
	 * 插入服务
	 * 
	 * @param wrapper
	 */
	public void insertService(ServiceWrapper wrapper) {
		if (wrapper == null)
			throw new IllegalArgumentException();

		ServiceWrapper tail = wrapper;
		while (tail.getService() instanceof ServiceWrapper)
			tail = (ServiceWrapper) tail.getService();
		if (tail.getService() != null)
			throw new IllegalArgumentException("必须从链尾插入！");

		IService next = getService();
		setService(wrapper);
		tail.setService(next);
	}

	@Override
	public void service(IRequest request, IResponse reponse) throws  Exception {
		IService curService = service;
		if (curService != null) {
			curService.service(request, reponse);
		}

	}

	/* ------------------------------------------------------------ */
	@Override
	protected void expandChildren(List<IService> list, Class<?> byClass) {
		expandHandler(service, list, byClass);
	}

}
