package com.waspring.framework.antenna.service;

import java.util.Arrays;
import java.util.List;

import com.waspring.framework.antenna.core.MultiException;
import com.waspring.framework.antenna.core.service.IService;
import com.waspring.framework.antenna.core.service.IServiceContainer;
import com.waspring.framework.antenna.core.service.ServiceException;
import com.waspring.framework.antenna.core.util.ArrayUtil;
import com.waspring.framework.antenna.core.visitor.IRequest;
import com.waspring.framework.antenna.core.visitor.IResponse;

/**
 * 集合式服务。执行就是所有服务执行，直到抛出异常
 * 
 * @author felly
 *
 */
public class ServiceCollection extends AbstractServiceContainer {

	@Override
	public IService[] getServices() {

		return services;
	}

	private final boolean mutableWhenRunning;
	private volatile IService[] services;

	/* ------------------------------------------------------------ */
	public ServiceCollection() {
		this(false);
	}

	/* ------------------------------------------------------------ */
	public ServiceCollection(IService... services) {
		this(false, services);
	}

	/* ------------------------------------------------------------ */
	public ServiceCollection(boolean mutableWhenRunning, IService... services) {
		this.mutableWhenRunning = mutableWhenRunning;
		if (services.length > 0)
			setServices(services);
	}

	public void setServices(IService[] services) {

		if (services != null) {
			// check for loops
			for (IService service : services)
				if (service == this || (service instanceof IServiceContainer
						&& Arrays.asList(((IServiceContainer) service).getChildServices()).contains(this)))
					throw new IllegalStateException("setHandler loop");

		}
		this.services = services;
	}

	public void service(IRequest request, IResponse reponse) throws  Exception {
		{
			if (services != null) {
				MultiException mex = null;

				for (int i = 0; i < services.length; i++) {
					try {
						services[i].service(request, reponse);
					}

					catch (RuntimeException e) {
						throw e;
					} catch (Exception e) {
						if (mex == null)
							mex = new MultiException();
						mex.add(e);
					}
				}
				if (mex != null) {
					if (mex.size() == 1)
						throw new ServiceException(mex.getThrowable(0));
					else
						throw new ServiceException(mex);
				}

			}
		}
	}

	/**
	 * 在末尾添加列队服务
	 * @param service
	 */
	public void addService(IService service) {
		setServices(ArrayUtil.addToArray(getServices(), service, IService.class));
	}

	/* ------------------------------------------------------------ */
	/*
	 *  在开始添加列队服务
	 */
	public void prependService(IService service) {
		setServices(ArrayUtil.prependToArray(service, getServices(), IService.class));
	}

	/* ------------------------------------------------------------ */
	public void removeService(IService service) {
		IService[] services = getServices();

		if (services != null && services.length > 0)
			setServices(ArrayUtil.removeFromArray(services, service));
	}

	/* ------------------------------------------------------------ */
	@Override
	protected void expandChildren(List<IService> list, Class<?> byClass) {
		if (getServices() != null)
			for (IService h : getServices())
				expandHandler(h, list, byClass);
	}
}
